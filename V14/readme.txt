此版本完成注册用户的业务操作

本着功能拆分的原则，不要将注册用户的实际操作写在ClientHandLer的处理请求环节，而是拆分到
一个专门处理业务的类中，然后让clientHandLer根据请求判断是处理什么业务并调用对应的处理业务类即可。