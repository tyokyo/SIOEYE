/*
 * This file is part of Toaster
 *
 * Copyright (c) 2014, 2017 Peter Siegmund
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.sioeye.toast;

import android.provider.BaseColumns;

/**
 * @author mars3142
 */
public class ToasterTable
        implements BaseColumns {

    private static final String TAG = ToasterTable.class.getSimpleName();
    public static final String TABLE_NAME = "toaster";
    public static final String TIMESTAMP = "timestamp";
    public static final String PACKAGE = "package";
    public static final String MESSAGE = "message";
    public static final String VERSION_CODE = "version_code";
    public static final String VERSION_NAME = "version_name";

}
