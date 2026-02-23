package com.vandemunconnect.util

import android.content.Context
import com.vandemunconnect.data.model.UserProfile
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File

object ExcelExporter {
    fun exportDelegates(context: Context, profiles: List<UserProfile>): File {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Delegates")

        val header = sheet.createRow(0)
        header.createCell(0).setCellValue("Name")
        header.createCell(1).setCellValue("Portfolio")
        header.createCell(2).setCellValue("Committee")

        profiles.sortedBy { it.name.lowercase() }.forEachIndexed { index, profile ->
            val row = sheet.createRow(index + 1)
            row.createCell(0).setCellValue(profile.name)
            row.createCell(1).setCellValue(profile.portfolio)
            row.createCell(2).setCellValue(profile.committee)
        }

        val outFile = File(context.cacheDir, "mun_delegates.xlsx")
        outFile.outputStream().use { workbook.write(it) }
        workbook.close()
        return outFile
    }
}
