package com.dirkeisold.easynote.usecases.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import com.dirkeisold.easynote.R
import com.dirkeisold.easynote.common.MyApplication
import com.dirkeisold.easynote.common.or
import com.dirkeisold.easynote.di.components.ActivityComponent
import com.dirkeisold.easynote.di.components.DaggerActivityComponent
import com.dirkeisold.easynote.di.interfaces.ActivityGraphDelegate
import com.dirkeisold.easynote.di.modules.ActivityModule
import com.dirkeisold.easynote.usecases.detail.presenter.DetailViewPresenter
import com.dirkeisold.easynote.usecases.detail.view.DetailView
import com.jakewharton.rxbinding.widget.RxTextView
import kotlinx.android.synthetic.main.activity_detail.*
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by dirkeisold on 12.03.16.
 *
 *
 * Copyright (c) 2018, Dirk Eisold
 *
 *
 * All rights reserved.
 *
 *
 * Activity showing the note details. Provides CRUD manipulation of data.
 *
 *
 * If no existing note id is passed along with {@see buildIntentForNewNote()} a new note will be created
 * but only persisted when saved.
 */
class DetailActivity : AppCompatActivity(), ActivityGraphDelegate, DetailView {

    override var activityGraph: ActivityComponent? = null

    private val titleEditText: EditText by lazy { activity_detail_title }
    private val textEditText: EditText by lazy { activity_detail_text }
    private val lastModifiedTextView: TextView by lazy { activity_detail_last_modified }

    @Inject
    lateinit var detailViewPresenter: DetailViewPresenter

    private var showSave = false
    private var showDelete = false
    private var callbacks: DetailView.Callbacks? = null
    private var subscribe: Subscription? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injectDI()

        setContentView(R.layout.activity_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupData(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        detailViewPresenter.attachView(this)
    }

    override fun onStop() {
        detailViewPresenter.detachView()

        subscribe?.let { if (!it.isUnsubscribed) it.unsubscribe() }

        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.run {
            (callbacks as DetailView.Callbacks).onSaveInstanceState(this)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.run {
            detailViewPresenter.onRestoreSavedInstanceState(savedInstanceState)
        }

        super.onRestoreInstanceState(savedInstanceState)
    }

    private fun setupData(savedInstanceState: Bundle?) {
        intent?.apply {
            with(getIntExtra(EXTRA_NOTE_ID, -1)) noteId@{

                Timber.d("setupData: note id=%d", this)

                with(detailViewPresenter) {
                    savedInstanceState?.run {
                        onRestoreSavedInstanceState(this)
                    }.or {
                        if (this@noteId == -1) createNewNote() else load(this@noteId)
                    }
                }
            }
        }
    }

    private fun setupTextChangeListener() {
        val observableTitle = RxTextView.textChanges(titleEditText).observeOn(AndroidSchedulers.mainThread())
        val observableText = RxTextView.textChanges(textEditText).observeOn(AndroidSchedulers.mainThread())

        // fire the callback when either titleEditText or text is >0 and both are not equal to their prior values
        subscribe = Observable.combineLatest(observableTitle, observableText) { cTitle, cText ->
            Pair(cTitle.toString(), cText.toString())
        }
                .subscribe { value -> callbacks?.onNoteChanged(value.first, value.second) }
    }


    private fun injectDI() {
        activityGraph = DaggerActivityComponent
                .builder()
                .applicationComponent(MyApplication.app?.applicationComponent)
                .activityModule(ActivityModule(this))
                .build()

        activityGraph?.inject(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.basic, menu)

        // adapt to state
        menu.findItem(R.id.menu_save)?.run { isVisible = showSave }
        menu.findItem(R.id.menu_delete)?.run { isVisible = showDelete }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        Timber.d("menu item id=%d clicked", itemId)

        when (itemId) {
        // action with ID action_refresh was selected
            R.id.menu_save -> {
                callbacks?.onSaveClicked()
                return true
            }

            R.id.menu_delete -> {
                callbacks?.onDeleteClicked()
                return true
            }

            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setActionBarTitle(title: String?) {
        supportActionBar?.title = title
    }

    override fun setTitle(title: String?) {
        titleEditText.setText(title)
    }

    override fun setText(text: String?) {
        textEditText.setText(text)
    }

    override fun setLastModified(lastModified: String) {
        lastModifiedTextView.text = lastModified
    }

    override fun setSettingDataFinished() {
        setupTextChangeListener()
    }

    override fun showSave() {
        showSave = true
        invalidateOptionsMenu()
    }

    override fun hideSave() {
        showSave = false
        invalidateOptionsMenu()
    }

    override fun showDelete() {
        showDelete = true
        invalidateOptionsMenu()
    }

    override fun setCallbacks(callbacks: DetailView.Callbacks) {
        this.callbacks = callbacks
    }

    override fun removeCallbacks() {
        callbacks = null
    }

    companion object {

        private val EXTRA_NOTE_ID = "note_id"

        fun buildIntentAndShowNote(context: Activity, noteId: Int?): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(EXTRA_NOTE_ID, noteId)
            return intent
        }

        fun buildIntentForNewNote(context: Activity): Intent {
            return Intent(context, DetailActivity::class.java)
        }
    }
}
