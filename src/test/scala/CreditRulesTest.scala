import model.credit.{Application, CreditLine, Customer, CustomerData, PendingApplication}
import org.drools.core.ClassObjectFilter
import org.kie.api.event.rule.{ObjectDeletedEvent, ObjectInsertedEvent, ObjectUpdatedEvent, RuleRuntimeEventListener}
import org.kie.api.runtime.KieContainer
import org.kie.api.runtime.rule.FactHandle
import org.scalatest.Matchers.convertToAnyShouldWrapper
import org.scalatest.flatspec.AnyFlatSpec
import org.slf4j.LoggerFactory

// TODO
class CreditRulesTest extends AnyFlatSpec {

  private val kieContainer: KieContainer = KnowledgeSessionHelper.createRuleBase()

  "test1" should "work" in {

    val statefulSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "my-credit-ksession")
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
    statefulSession.setGlobal("logger", LoggerFactory.getLogger(classOf[CreditRulesTest]))
    val customer: Customer       = new Customer("Pablo", 16)
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

  "test2" should "work" in {

      val customerData = CustomerData("1000", 100)
      val creditLine = CreditLine(customerData, 100000)
      val application = PendingApplication(customerData.id, Some(creditLine))
      // set up example Customer data


      val session = kieContainer.newKieSession( "my-credit-ksession" )
      session.insert( application )
      session.insert( customerData )
      session.fireAllRules()

      val objects = session.getObjects( new ClassObjectFilter( classOf[ Application ] ) )
      objects.size shouldBe 1

      val i = objects.iterator()
      val output = i.next().asInstanceOf[ Application ]

      // perform assertions

    }

}