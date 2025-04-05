package org.example

import org.example.ui.MainWindow
import javax.swing.SwingUtilities

fun main() {
    SwingUtilities.invokeLater {
        MainWindow().isVisible = true
    }
}