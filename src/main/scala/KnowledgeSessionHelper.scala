import org.drools.core.event.DebugAgendaEventListener
import org.kie.api.KieServices
import org.kie.api.event.rule.{ObjectDeletedEvent, ObjectInsertedEvent, ObjectUpdatedEvent, RuleRuntimeEventListener}
import org.kie.api.runtime.{KieContainer, KieSession, StatelessKieSession}

object KnowledgeSessionHelper {

  def createRuleBase(): KieContainer = {
    val ks: KieServices = KieServices.Factory.get()
    ks.getKieClasspathContainer()
  }

  def getStatelessKnowledgeSession(kieContainer: KieContainer, sessionName: String): StatelessKieSession =
    kieContainer.newStatelessKieSession(sessionName)

  def getStatefulKnowledgeSession(kieContainer: KieContainer, sessionName: String): KieSession =
    kieContainer.newKieSession(sessionName)

  def getStatefulKnowledgeSessionWithCallback(kieContainer: KieContainer, sessionName: String): KieSession = {
    val session: KieSession = getStatefulKnowledgeSession(kieContainer, sessionName)

    session.addEventListener(
      new RuleRuntimeEventListener {
        override def objectInserted(event: ObjectInsertedEvent): Unit =
          println(s"Object inserted: ${event.getObject.toString}")

        override def objectUpdated(event: ObjectUpdatedEvent): Unit =
          println(s"Object updated: ${event.getObject.toString}")

        override def objectDeleted(event: ObjectDeletedEvent): Unit =
          println(s"Object deleted: ${event.getOldObject.toString}")
      }
    )

    //    session.addEventListener(
    //      new AgendaEventListener {
    //        def matchCreated(event: MatchCreatedEvent): Unit =
    //          println(s"The rule ${event.getMatch().getRule().getName()} can be fired in agenda")
    //
    //        def matchCancelled(event: MatchCancelledEvent): Unit =
    //          println(s"The rule ${event.getMatch().getRule().getName()} cannot be in agenda")
    //
    //        def beforeMatchFired(event: BeforeMatchFiredEvent): Unit =
    //          println(s"The rule ${event.getMatch().getRule().getName()} will be fired")
    //
    //        def afterMatchFired(event: AfterMatchFiredEvent): Unit =
    //          println(s"The rule ${event.getMatch().getRule().getName()} has be fired")
    //
    //        def agendaGroupPopped(event: AgendaGroupPoppedEvent): Unit = {}
    //
    //        def agendaGroupPushed(event: AgendaGroupPushedEvent): Unit = {}
    //
    //        def beforeRuleFlowGroupActivated(event: RuleFlowGroupActivatedEvent): Unit = {}
    //
    //        def afterRuleFlowGroupActivated(event: RuleFlowGroupActivatedEvent): Unit = {}
    //
    //        def beforeRuleFlowGroupDeactivated(event: RuleFlowGroupDeactivatedEvent): Unit = {}
    //
    //        def afterRuleFlowGroupDeactivated(event: RuleFlowGroupDeactivatedEvent): Unit = {}
    //      }
    //    )
    session.addEventListener(new DebugAgendaEventListener)
    session
  }

}