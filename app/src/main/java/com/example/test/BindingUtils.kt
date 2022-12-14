/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.test

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.test.data.Result
import com.example.test.Users

@BindingAdapter("idText")
fun TextView.setIdText(item: Result?) {
    item?.let {
        text = item.id.toString()
    }
}



@BindingAdapter("timeText")
fun TextView.setTimeText(item: Result?) {
    item?.let {
        text = item.time.toString()
    }
}

@BindingAdapter("dateText")
fun TextView.setDateText(item: Result?) {
    item?.let {
        text = item.date
    }
}