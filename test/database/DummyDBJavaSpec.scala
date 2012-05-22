package database

import java.DummyDBJava
import org.specs2.mutable.Specification

/**
 * @author Arnaud Tanguy <arnaud@fivecool.net>
 *         Date: 22/05/2012
 *
 */
class DummyDBJavaSpec extends Specification {
  val db = DummyDBJava.INSTANCE
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
      db.get("key2") must beEqualTo(null)
    }

    "return the data set before" in {
      db.put("key2", "data")
      val res = db.get("key2")
      res must beEqualTo("data")
    }
  }

  "datastore set operation " should {
    "set value ot a key" in {
      db.put("key3", "new data")
      val res = db.get("key3")
      res must beEqualTo("new data")
    }
  }
}
