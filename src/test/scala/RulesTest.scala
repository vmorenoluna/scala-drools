import model.Customer
import org.kie.api.event.rule.{ObjectDeletedEvent, ObjectInsertedEvent, ObjectUpdatedEvent, RuleRuntimeEventListener}
import org.kie.api.runtime.KieContainer
import org.kie.api.runtime.rule.FactHandle
import org.scalatest.Matchers.convertToAnyShouldWrapper
import org.scalatest.flatspec.AnyFlatSpec
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.mutable

class RulesTest extends AnyFlatSpec {

  private val kieContainer: KieContainer = KnowledgeSessionHelper.createRuleBase()

  "session" should "work" in {

    val statefulSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "my-first-ksession")
    statefulSession.addEventListener(
      new RuleRuntimeEventListener {
        override def objectInserted(event: ObjectInsertedEvent): Unit =
          println(s"Object inserted: ${event.getObject.toString}")

        override def objectUpdated(event: ObjectUpdatedEvent): Unit =
          println(s"Object updated: ${event.getObject.toString}")

        override def objectDeleted(event: ObjectDeletedEvent): Unit =
          println(s"Object deleted: ${event.getOldObject.toString}")
      }
    )
    statefulSession.setGlobal("logger", LoggerFactory.getLogger(classOf[RulesTest]))
    val customer: Customer       = new Customer("Vincenzo", 16)
    val factHandle: FactHandle = statefulSession.insert(customer)
    statefulSession.fireAllRules()
    customer.setAge(20)
    statefulSession.update(factHandle, customer)
    statefulSession.fireAllRules()
    statefulSession.delete(factHandle)
    statefulSession.fireAllRules()

    val n = 1
    n shouldEqual 1
  }

}