package other

import cats.effect.unsafe.implicits.global
import cats.effect.{ExitCode, IO, IOApp, Ref}
import other.Memo2.getClass

import java.nio.file.{Path, Paths}
import scala.concurrent.duration._
import scala.io.Source
import java.nio.file.Paths
import scala.io.Source
import scala.util.matching.Regex
import cats.effect.{IO, IOApp}
import java.nio.file.{Paths, Path, StandardOpenOption}
import java.nio.charset.StandardCharsets
import java.nio.file.Files

object IO_Practice extends IOApp {
  def run(args: List[String]): IO[ExitCode] = {

    def fileOutput = for {
      _ <- copyFileToDisk()  // Копируем файл из ресурсов в рабочую директорию
      content <- fileRead()  // Читаем содержимое
      _ <- writeToFile(content.toUpperCase)  // Записываем измененное содержимое
      _ <- IO(println("Content written to file"))
    } yield ()

//    fileOutput().flatMap(result => IO.println(result)).as(ExitCode.Success)
    doCachingCall.as(ExitCode.Success)
  }

  def consoleInput: IO[Unit] =
    for {
      _    <- IO(println("Enter your name"))
      name <- IO.readLine
      _    <- IO(println(s"Hello $name"))
    } yield ()

  def timer: IO[Unit] =
    for {
    _   <- IO(println("Timer start"))
    _   <- IO.sleep(2.seconds)
    _   <- IO(println("Timer end"))
  } yield()

  def callForFilePath: IO[Unit] = {
    val result = for {
     filePath <- IO.delay {
      Paths.get(getClass.getResource("/sample2.txt").toURI)
    }
    _ <- IO(println(s"File path: $filePath"))

  } yield ()

    result.handleErrorWith { error =>
      IO(println(s"Failed to read file due to: $error"))
    }
  }

  // Cache
  def fakeApiCall(): IO[String] = for {
    _ <- IO.sleep(3.seconds)
    result = "{\n\"name\": \"John\",\n\"age\" : \"40\"\n}"
  } yield result

  def caching(ref: Ref[IO, Option[String]]): IO[String] = {
    ref.get.flatMap {
      case Some(cached) => IO.pure(cached)
      case None =>
        fakeApiCall().flatMap { result =>
          ref.set(Some(result)).as(result)
        }
    }
  }


  def doCachingCall(): IO[Unit] = for {
    ref <- Ref.of[IO, Option[String]](None)
    _ <- IO(println("Calling for API..."))
    _ <- ref.set(Some("1"))
    value <- ref.get
    _ <- IO(println(value))
    _ <- ref.set(Some("2"))
    value <- ref.get
    _ <- IO(println(value))
    firstCall <- caching(ref)
    _ <- IO(println(s"First Call Result: $firstCall"))
    _ <- IO(println("Wait before two next API calls"))
    _ <- IO.sleep(2.seconds)
    secondCall <- caching(ref)
    _ <- IO(println(s"Second Call Result: $secondCall"))
    thirdCall <- caching(ref)
    _ <- IO(println(s"Second Call Result: $thirdCall"))
  } yield ()

  def calculation(): IO[Unit] = for {
    _ <- IO(println("Enter value for a:"))
    aStr <- IO.readLine
    _ <- IO(println("Enter value for b:"))
    bStr <- IO.readLine
    a = aStr.toIntOption.getOrElse(0)
    b = bStr.toIntOption.getOrElse(0)
    _ <- IO(println(s"Result: ${(a + b) * 10}"))
  } yield ()

  // File Read
//  def fileRead(): IO[String] = {
//    def filePath: IO[Option[Path]] = IO.delay {
//      Option(getClass.getResource("/sample.txt")).map(res => Paths.get(res.toURI))
//    }
//
//    def fileSource(path: Path): IO[String] = IO.delay {
//      val source = Source.fromFile(path.toFile)
//      val content = source.getLines().mkString("\n")
//      source.close()
//      content.toUpperCase
//    }
//
//    filePath.flatMap {
//      case Some(path) => fileSource(path)
//      case None => IO("")
//    }
//  }

  def resourceFilePath: Path = Paths.get(getClass.getResource("/sample.txt").toURI)

  def diskFilePath: Path = Paths.get("sample.txt")

  def copyFileToDisk(): IO[Unit] = IO.delay {
    if (!Files.exists(diskFilePath)) {
      Files.copy(resourceFilePath, diskFilePath)
    }
  }

  def fileRead(): IO[String] = {
    IO {
      val bytes = Files.readAllBytes(diskFilePath)
      new String(bytes, StandardCharsets.UTF_8)
    }
  }

  def writeToFile(content: String): IO[Unit] = {
    IO {
      Files.write(diskFilePath, content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.WRITE, StandardOpenOption.CREATE)
    }
  }


}

