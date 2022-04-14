package model.credit

import scala.beans.BeanProperty

class Customer(
                @BeanProperty
                var name: String,
                @BeanProperty
                var age: Int
              )
