package database.scala


/**
 * DummyDBScala is the Scala implementation of our DataStore
 * @author Arnaud Tanguy <arnaud@fivecool.net>
 *         Date: 22/05/2012
 *
 */
object DummyDBScala {

  import database.StorageException

  private var db = Map[String, Any]()

  /**
   * Put the string value of a key
   * @param key
   * @param value
   * @throws database.StorageException
   */
  @throws(classOf[StorageException])
  def put(key: String, value: String): Unit = synchronized {
    val test = scala.math.random * (15 - 0)
    if (test < 2)
      throw new StorageException("Simulated store failure " + value)
    db += (key -> value)
  }

  /**
   * Get the value of a key
   * @param key
   * @throws database.StorageException
   * @return
   */
  @throws(classOf[StorageException])
  def get(key: String): Option[String] = synchronized {
    val test = scala.math.random * (15 - 0)
    if (test < 1)
      throw new StorageException("Simulated read failure " + key)
    db.get(key).map(res => Some(db.get(key).get.toString)).getOrElse(None)
  }

  /**
   * Increment the integer value of a key by the given amount
   * @param key
   * @param amount
   * @throws database.StorageException
   * @return
   */
  @throws(classOf[StorageException])
  def increment(key: String, amount: Long): Long = synchronized {
    val test = scala.math.random * (15 - 0)
    if (test < 1)
      throw new StorageException("Simulated store failure " + amount)
    if (db.contains(key)) {
      val oldValue = db.get(key)
      if (oldValue.isInstanceOf[Option[Any]]) {
        if (oldValue.asInstanceOf[Option[Any]].get.isInstanceOf[Long]) {
          val newValue = amount + oldValue.asInstanceOf[Option[Long]].get
          db += (key -> newValue)
          return newValue
        } else {
          throw new StorageException("value of key " + key + " is not a counter")
        }
      } else {
        throw new StorageException("value of key " + key + " is not a counter")
      }
    } else {
      db += (key -> amount)
      return amount
    }
  }
}
