package database

import org.specs2.mutable.Specification
import scala.DummyDBScala

/**
 * @author Arnaud Tanguy <arnaud@fivecool.net>
 *         Date: 22/05/2012
 *
 */
class DummyDBScalaSpec extends Specification {
  val db = DummyDBScala
  "datastore increment operation " should {
    "increment data for a new key" in {
      val res = db.increment("key", 2)
      res must beEqualTo(2)
    }

    "increment data for an existing key" in {
      val res = db.increment("key", 3)
      res must beEqualTo(5)
    }

    "throw an exception if increment a non Long value" in {
      db.put("test", "ok")
      db.increment("test", 2) must throwAn[StorageException]
    }
  }

  "datastore get operation " should {
    "return None if the key is not set" in {
      val res = db.get("key2")
      res must beEqualTo(None)
    }

    "return the data set before" in {
      db.put("key2", "data")
      val res = db.get("key2")
      res.isDefined must beEqualTo(true)
      res.get must beEqualTo("data")
    }
  }

  "datastore set operation " should {
    "set value ot a key" in {
      db.put("key3", "new data")
      val res = db.get("key3")
      res.isDefined must beEqualTo(true)
      res.get must beEqualTo("new data")
    }
  }
}
