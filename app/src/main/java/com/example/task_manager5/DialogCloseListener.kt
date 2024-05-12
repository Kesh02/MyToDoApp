package com.example.task_manager5


import android.content.DialogInterface

interface DialogCloseListener {
    fun handleDialogClose(dialog: DialogInterface)//This method is responsible for handling the event when a dialog is closed
}
