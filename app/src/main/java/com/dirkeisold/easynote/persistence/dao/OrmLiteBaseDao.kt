package com.dirkeisold.easynote.persistence.dao

import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.support.ConnectionSource

import java.sql.SQLException

/**
 * Created by dirkeisold on 16.03.15.
 *
 *
 * (C) Copyright (c) 2018, Dirk Eisold
 *
 *
 * Concrete implementation of the base dao impl
 */
class OrmLiteBaseDao<T, ID> @Throws(SQLException::class)
constructor(connectionSource: ConnectionSource, dataClass: Class<T>) : BaseDaoImpl<T, ID>(connectionSource, dataClass)