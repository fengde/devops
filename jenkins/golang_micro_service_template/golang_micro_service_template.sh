git clone git@github.com:fengde/golang-micro-service-template.git
app_name=$1
echo "开始构建$app_name"
cp -fr golang-micro-service-template/template $app_name
tar zcf $app_name.tar.gz $app_name
printenv