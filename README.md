program arguments/程序参数配置:
--publicKey=MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKtjXqIZD7PaJPYoKiArTIFc74OakbplY/Iu7pr1atcP5f0Dm8Ot3TiUoQEbxX6u5ivIHclwKYe/PEWAJl4WYl0CAwEAAQ==
Environment variables/系统变量参数配置:
nacos_discovery_group=MINI_TOOL_BOX_A_GROUP

application.yml配置：
${nacos_discovery_group} 作用于：在共享注册中心的时候做到服务分组隔离，需每一个开发人员自行配置，根据自定义规则配置不同的系统环境变量，在idea中配置

windows版nacos启动命令：./startup.cmd -m standalone