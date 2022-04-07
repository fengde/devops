#!groovy

pipeline {
    agent any

    options {
        // 过程打印时间戳
        timestamps()
        // 超时控制 5min
        timeout(5)
        // 不允许同时执行此流水线，防止资源冲突
        // disableConcurrentBuilds()
        // 跳过默认仓库的检出
        skipDefaultCheckout()
        // 重试次数
        // retry(1)
    }

    environment {
        APP_NMAE = 'fedel'
    }

    parameters {
        string(name: 'DEPLOY_ENV', defaultValue: 'staging', description: '')
        booleanParam(name: 'DEBUG_BUILD', defaultValue: true, description: '')
    }

    // triggers {
    //     cron('H */4 * * 1-5')
    // }

    // tools {
    //     maven 'apache-maven-3.0.1'
    // }

    stages {
        stage('01') {
            options {
                // retry(2)
                timeout(1)
            }
            // 符合条件才会继续执行当前stage
            when {
                anyOf {
                    environment name: 'DEPLOY_ENV', value: 'hello'
                }
            }
            environment {
                // // 可以访问维护的凭证
                // AN_ACCESS_KEY = credentials('my-prefined-secret-text')
                LOCAL_ENV = '测试局部变量'
            }
            input {
                message '人工录入信息，并且决定是否继续执行'
                ok '继续吧'
                submitter 'alice,admin'
                parameters {
                    string(name: 'PERSON', defaultValue: 'Mr Jenkins', description: '请填写您的名字')
                }
            }
            steps {
                echo 'stage 01'
                // sh 'maven --version'
                sh """
                    echo ${PERSON}
                    echo ${LOCAL_ENV}
                    printenv
                """
            }
        }
        stage('02') {
            steps {
                echo 'stage 02'
            }
        }
    }

    post {
        always {
            echo 'i am always'
        }

        success {
            echo 'success'
        }

        failure {
            echo 'failure'
        }

        aborted {
            echo 'abortd'
        }
    }
}