package uz.kvikk.file.utils.converter

import org.jooq.impl.AbstractConverter

import java.sql.Date

class DateConverter : AbstractConverter<Date, java.util.Date>(Date::class.java, java.util.Date::class.java) {

  override fun from(t: Date?): java.util.Date? {
    return if (t == null) null else java.util.Date(t.time)
  }

  override fun to(u: java.util.Date?): Date? {
    return if (u == null) null else Date(u.time)
  }
}

