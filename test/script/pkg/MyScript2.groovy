package script.pkg

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/16/13
 * Time: 6:50 PM
 * To change this template use File | Settings | File Templates.
 */
 doesntExist
doesntExist

this.@myattrib

def xml='''
<root>
    <node attrib='123'>prop</node>
</root>
'''

def slurper = new XmlSlurper().parseText(xml)

slurper.root.node.@attrib

doesntExist='LOL'

nonExistantMethod(doesntExist)

noArgs()

def myMethod(arg){

}

method2 = {arg->myMethod(arg)}

def property=2