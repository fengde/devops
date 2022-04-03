git clone git@github.com:fengde/golang-micro-service-template.git
app_name=$1
echo "开始构建${app_name}微服务"
cp -fr golang-micro-service-template/template $app_name
mv $app_name/template.go $app_name/$app_name.go
mv $app_name/etc/template-api.yaml $app_name/etc/$app_name-api.yaml
sed -i''  "s?template/internal?${app_name}/internal?g" $app_name/$app_name.go
sed -i''  "s?template-api.yaml?${app_name}-api.yaml?" $app_name/$app_name.go
sed -i''  "s?module template?module ${app_name}?" $app_name/go.mod
sed -i''  "s?project='template'?project='${app_name}'?" $app_name/control.sh
sed -i''  "s?template.go?${app_name}.go?" $app_name/Dockerfile
sed -i''  "s?template-api?${app_name}-api?" $app_name/etc/$app_name-api.yaml
sed -i''  "s?template/internal?${app_name}/internal?g" `grep -rl "$old_app_name/internal" $app_name/internal`

tar zcf $app_name.tar.gz $app_name

echo "已生成$app_name.tar.gz，可直接下载"