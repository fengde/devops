#!groovy

//构建参数
String targetHosts = "${env.targetHosts}"

//saltapi模板
def Salt(salthost,saltfunc,saltargs) {
    result = salt(authtype: 'pam', 
                clientInterface: local( arguments: saltargs,
                                        function: saltfunc, 
                                        target: salthost, 
                                        targettype: 'list'),
                credentialsId: "f89abde3-49f0-4b75-917e-c4e49c483f4f", 
                servername: "http://0.0.0.0:9000")
   
    println(result)
    PrintMes(result,'blue')
    return  result
}

node("master"){
    stage("Deploy"){
        Salt("${targetHosts}","cmd.run","ls")
    }
}