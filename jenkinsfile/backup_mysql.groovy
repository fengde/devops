#!groovy

pipeline {
    agent any
    options {
        timeout(30)
        timestamps()
    }

    parameters {
        string(name: 'mysql_host', defaultValue: '', description: 'db地址')
        string(name: 'mysql_port', defaultValue: '3306', description: 'db端口')
        string(name: 'mysql_user', defaultValue: 'root', description: 'db用户名')
        string(name: 'mysql_password', defaultValue: '', description: 'db密码')
        string(name: 'mysql_db', defaultValue: '', description: 'db名称')
        string(name: 'mysql_ignore_table', defaultValue: '',
            description: '跳过某些表：--ignore-table=db.table1 --ignore-table=db.table2')
    }

    stages {
        stage('do_01') {
            steps {
                sh """
                    mysqldump -h${mysql_host} -P${mysql_port} -u${mysql_user} -p${mysql_password} --skip-lock-tables ${mysql_ignore_table} ${mysql_db} > ${mysql_db}_`date '+%Y-%m-%d'`.sql
                """
                archiveArtifacts artifacts: "${mysql_db}_`date '+%Y-%m-%d'`.sql", followSymlinks: false
            }
        }
    }
}
