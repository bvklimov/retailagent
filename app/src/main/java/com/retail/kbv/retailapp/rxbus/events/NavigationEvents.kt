package com.retail.kbv.retailapp.rxbus.events

import com.retail.kbv.retailapp.ui.activities.BaseActivity
import lombok.Data
import lombok.EqualsAndHashCode

@Data
@EqualsAndHashCode(callSuper = false)

class BackPressedEvent: RxBusEvent()