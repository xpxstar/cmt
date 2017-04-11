package cn.ac.iscas.cmt.v2.util;

import cn.ac.iscas.cmt.v2.model.entity.Category;

public class StaticCategory {
	public static Category root;
	static{
		Category ssh = new Category("/system/security/ssh/");
		ssh.addSub(new Category("/system/security/ssh/openssh/"));
		Category fireall = new Category("/system/networking/firewall/");
		fireall.addSub(new Category("/system/networking/firewall/iptables/"));
		
		Category edit = new Category("/system/file/editor/");
		fireall.addSub(new Category("/system/file/editor/vim/"));
		
		Category proxy = new Category("/server/webserver/proxy/");
		proxy.fillSub("/server/webserver/proxy/nginx/","/server/webserver/proxy/haproxy/");
		
		Category util = new Category("/system/utility/");
		util.fillSub("/system/utility/backup/","/system/utility/bash/","/system/utility/supervisor/");
		
		Category time = new Category("/system/time/");
		time.fillSub("/system/time/cron/","/system/time/ntp/","/system/time/timezone/");
		
		Category security = new Category("/system/security/");
		security.fillSub("/system/security/ssl/","/system/security/sudo/");
		security.addSub(ssh);
		
		Category networking = new Category("/system/networking/");
		networking.fillSub("/system/networking/vpn/","/system/networking/dns");
		networking.addSub(fireall);
		
		Category monitoring = new Category("/system/monitoring/");
		monitoring.fillSub("/system/monitoring/graphite/","/system/monitoring/nagios/","/system/monitoring/zabbix/");
		
		Category file = new Category("/system/file/");
		file.fillSub("/system/file/nfs/");
		file.addSub(edit);
		
		Category webserver = new Category("/server/webserver/");
		webserver.fillSub("/server/webserver/apache/","/server/webserver/cache/","/server/webserver/tomcat/","/server/webserver/varnish/");
		webserver.addSub(proxy);
		
		Category email = new Category("/server/email/");
		email.fillSub("/server/email/postfix/");
		
		Category cms = new Category("/server/cms/");
		cms.fillSub("/server/cms/drupal/","/server/cms/wordpress/");
		
		Category ruby = new Category("/runtime/ruby/");
		ruby.fillSub("/runtime/ruby/rails/");
		
		Category repository = new Category("/packaging/repository/");
		repository.fillSub("/packaging/repository/apt/","/packaging/repository/yum/");
		
		Category message = new Category("/distributed/messaging/");
		message.fillSub("/distributed/messaging/rabbitmq/");
		
		Category clustering = new Category("/distributed/clustering/");
		clustering.fillSub("/distributed/clustering/consul/","/distributed/clustering/zookeeper/");
		
		Category cloud = new Category("/distributed/cloud");
		Category aws = new Category("/distributed/cloud/aws");
		cloud.addSub(aws);cloud.fillSub("/distributed/cloud/openstack/");
		aws.fillSub("/distributed/cloud/aws/ec2/");
		
		Category testing = new Category("/development/testing/");
		clustering.fillSub("/development/testing/motd/");
		
		Category delivery = new Category("/development/delivery/");
		delivery.fillSub("/development/delivery/jenkins/");
				
		Category sql = new Category("/database/sql/");
		sql.fillSub("/database/sql/postgresql/","/database/sql/mariadb/","/database/sql/mysql/","/database/sql/oracle/");
		
		Category nosql = new Category("/database/nosql/");
		nosql.fillSub("/database/nosql/mongodb/","/database/nosql/redis/","/database/nosql/elasticsearch/");
		
		
		Category virtualization = new Category("/virtualization/");
		virtualization.fillSub("/virtualization/container/","/virtualization/vagrant/");
		
		Category system = new Category("/system/");
		system.addSub(util,time,security,networking,monitoring,file);
		system.fillSub("/system/authentication/");
		
		Category server = new Category("/server/");
		server.addSub(cms,email,webserver);
		
		Category runtime = new Category("/runtime/");
		runtime.addSub(ruby);
		runtime.fillSub("/runtime/go/","/runtime/java/","/runtime/nodejs/","/runtime/php/","/runtime/python/");
		
		Category packaging = new Category("/packaging/");
		packaging.addSub(repository);
		
		Category logging = new Category("/logging/");
		logging.fillSub("/logging/kibana/","/logging/logrotate/","/logging/logstash/","/logging/rsyslog/");
		
		Category distributed = new Category("/distributed/");
		distributed.addSub(cloud,clustering,message);
		
		Category development = new Category("/development/");
		development.addSub(delivery,testing);
		development.fillSub("/development/version-control/");
		
		Category database = new Category("/database/");
		database.addSub(sql,nosql);
		database.fillSub("/database/influxdb/");
		
		root = new Category("root");
		root.addSub(database,development,distributed,logging,packaging,runtime,server,system,virtualization);
			
		
	}
}
