package part8

import cats.effect.IO


object part8 {

  def main(args: Array[String]) = {

    case class MeetingTime(startHour: Int, endHour: Int)

    def possibleMeetings(existingMeetings: List[MeetingTime],startHour: Int, endHour: Int,lengthHours: Int): List[MeetingTime] = ???

    def schedule(person1: String, person2: String, lengthHours: Int): IO[Option[MeetingTime]] = ???

  }

}
