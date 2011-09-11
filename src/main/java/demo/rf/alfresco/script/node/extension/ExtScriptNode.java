package demo.rf.alfresco.script.node.extension;

import org.alfresco.repo.jscript.ScriptNode;
import org.alfresco.repo.processor.BaseProcessorExtension;
import org.alfresco.repo.rule.RuleModel;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;

/***
 * 
 * This class is meant for Javascript API extension allowing for copy of
 * rule sets from one node to the other and creation of child nodes with special
 * kinds of child-association.
 * 
 * @author Rui Fernandes
 *
 */
public class ExtScriptNode extends BaseProcessorExtension {

	private NodeService nodeService;

	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	public void addNode(ScriptNode parent, ScriptNode child,
			String assocTypeQName, String qname) {
		nodeService.addChild(parent.getNodeRef(), child.getNodeRef(),
				QName.createQName(assocTypeQName), QName.createQName(qname));
	}

	public void linkRuleSet(ScriptNode source, ScriptNode target) {
		nodeService
				.addAspect(target.getNodeRef(), RuleModel.ASPECT_RULES, null);
		nodeService.removeChild(target.getNodeRef(), getFirstChild(target));
		nodeService.addChild(target.getNodeRef(), getFirstChild(source),
				RuleModel.ASSOC_RULE_FOLDER, RuleModel.ASSOC_RULE_FOLDER);
	}

	private NodeRef getFirstChild(ScriptNode node) {
		return nodeService.getChildAssocs(node.getNodeRef()).get(0)
				.getChildRef();
	}
}
