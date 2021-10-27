package elsevier.hackday.coffeetime

import net.fortuna.ical4j.model.{Calendar, DateTime}
import net.fortuna.ical4j.util.RandomUidGenerator

import java.text.SimpleDateFormat
import java.time.{Instant, LocalDateTime, OffsetDateTime, ZoneId}
import java.time.format.{DateTimeFormatter, DateTimeFormatterBuilder}

object Scratchpad {

  private val iso8601DateTimeFormatter = new DateTimeFormatterBuilder()
    .append(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    .optionalStart
    .appendOffset("+HH:MM", "+00:00")
    .optionalEnd
    .optionalStart
    .appendOffset("+HHMM", "+0000")
    .optionalEnd
    .optionalStart
    .appendOffset("+HH", "Z")
    .optionalEnd
    .toFormatter

  private def parseDateTime(s: String): DateTime = {
    val offsetDateTime = OffsetDateTime.parse(s, iso8601DateTimeFormatter).atZoneSameInstant(ZoneId.of("UTC"))
    new DateTime(offsetDateTime.toInstant.toEpochMilli)
  }


//  def main(args: Array[String]): Unit = {
//    import net.fortuna.ical4j.model.{DateTime, TimeZoneRegistryFactory}
//    import net.fortuna.ical4j.model.component.VEvent
//    import net.fortuna.ical4j.model.parameter.{Cn, Role}
//
//    import net.fortuna.ical4j.model.property.{Attendee, CalScale, ProdId}
//    import java.net.URI
//
//    System.setProperty("net.fortuna.ical4j.timezone.cache.impl", "net.fortuna.ical4j.util.MapTimeZoneCache")
//
//    // Create a TimeZone// Create a TimeZone
//    val registry = TimeZoneRegistryFactory.getInstance.createRegistry
//    val timezone = registry.getTimeZone("America/Mexico_City")
//
//    println(iso8601DateTimeFormatter.format(LocalDateTime.now()))
//
//    val tz = timezone.getVTimeZone
//
//    val start = parseDateTime("2021-03-05T21:00:00Z")
//    val end = parseDateTime("2021-03-05T22:00:00Z")
//
//    // Create the event
//    val eventName = "Progress Meeting"
//    val meeting = new VEvent(start, end, eventName)
//
//    // add timezone info..
//    meeting.getProperties.add(tz.getTimeZoneId)
//
//    // generate unique identifier..
//    val ug = new RandomUidGenerator()
//    val uid = ug.generateUid
//    meeting.getProperties.add(uid)
//
//    // add attendees..
//    val dev1 = new Attendee(URI.create("mailto:dev1@mycompany.com"))
//    dev1.getParameters.add(Role.REQ_PARTICIPANT)
//    dev1.getParameters.add(new Cn("Developer 1"))
//    meeting.getProperties.add(dev1)
//
//    val dev2 = new Attendee(URI.create("mailto:dev2@mycompany.com"))
//    dev2.getParameters.add(Role.OPT_PARTICIPANT)
//    dev2.getParameters.add(new Cn("Developer 2"))
//    meeting.getProperties.add(dev2)
//
//    // Create a calendar
//    val icsCalendar = new Calendar()
//    icsCalendar.getProperties.add(new ProdId("-//Events Calendar//iCal4j 1.0//EN"))
//    icsCalendar.getProperties.add(CalScale.GREGORIAN)
//
//
//    // Add the event and print
//    icsCalendar.getComponents.add(meeting)
//    System.out.println(icsCalendar)
//
//    println("Done")
//  }
}
