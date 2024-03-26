program arguments/程序参数配置:
--publicKey=MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKtjXqIZD7PaJPYoKiArTIFc74OakbplY/Iu7pr1atcP5f0Dm8Ot3TiUoQEbxX6u5ivIHclwKYe/PEWAJl4WYl0CAwEAAQ==
Environment variables/系统变量参数配置:
nacos_discovery_group=MINI_TOOL_BOX_A_GROUP

application.yml配置：
${nacos_discovery_group} 作用于：在共享注册中心的时候做到服务分组隔离，需每一个开发人员自行配置，根据自定义规则配置不同的系统环境变量，在idea中配置

windows版nacos启动命令：./startup.cmd -m standalone
windows版nacos停止命令：./shutdown.cmd
windows版minio启动命令: minio.exe server D:\my_project\project\minio\home
git查看远程仓库命令： git remote -v 


备份地址：https://github.com/Jinhx128/springboot-demo?tab=readme-ov-file