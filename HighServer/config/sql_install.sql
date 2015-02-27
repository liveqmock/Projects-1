CREATE TABLE FRAME_ACL_ENTRY(
			ObjectID varchar(32) NOT NULL,
			SecurityID varchar(32) NOT NULL,
			PowerType integer NOT NULL,
			GrantCount integer NOT NULL,
			ModuleName varchar(200) NULL,
		 CONSTRAINT PK_FRAME_ACL_ENTRY PRIMARY KEY (ObjectID,SecurityID,PowerType)
	)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

CREATE TABLE FRAME_ATTACH(
			AttachID varchar(32) NOT NULL,
			FileName varchar(200) NOT NULL,
			FileSize integer NOT NULL,
			ContentType varchar(200) NOT NULL,
			Content LONGBLOB NULL,
			CreateTime timestamp NULL,
			FileType integer NOT NULL,
			FileDir varchar(1000) NULL,
		 CONSTRAINT PK_FRAME_ATTACH PRIMARY KEY (AttachID)
	)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

CREATE TABLE FRAME_Editor_ATTACH(
			AttachID varchar(32) NOT NULL,
			FileName varchar(200) NOT NULL,
			FileSize integer NOT NULL,
			ContentType varchar(200) NOT NULL,
			Content LONGBLOB NULL,
			CreateTime timestamp NULL,
			FileType integer NOT NULL,
			FileDir varchar(1000) NULL,
			PageSize integer NULL,
			VideoTime varchar(50) NULL,
			VideoViewCount integer NULL,
			UserName  varchar(200)  NULL,
		 CONSTRAINT PK_FRAME_Editor_ATTACH PRIMARY KEY (AttachID)
	)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;
	
CREATE TABLE  FRAME_ATTACH_DIR(
			AttachID varchar(32) NOT NULL,
			FileName varchar(200) NOT NULL,
			FileSize integer NOT NULL,
			ContentType varchar(200) NOT NULL,
			FileDir varchar(1000) NOT NULL,
			CreateTime TIMESTAMP NULL,
		 CONSTRAINT PK_FRAME_ATTACH_DIR PRIMARY KEY  (AttachID)
	)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

CREATE TABLE FRAME_ATTACH_RELATION(
			ObjectID varchar(32) NOT NULL,
			AttachID varchar(32) NOT NULL,
			AttachType integer NOT NULL,
		 CONSTRAINT PK_FRAME_ATTACH_RELATION PRIMARY KEY (ObjectID,AttachID)
	)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;
 
CREATE TABLE FRAME_DICTIONARY_KIND(
            KindID integer  NOT NULL,
			KindName varchar(200) NOT NULL,
			KindDes varchar(200) NOT NULL,
			ParentID integer NOT NULL,
		 CONSTRAINT PK_FRAME_DICTIONARY_KIND PRIMARY KEY (KindID)
		 )ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;
 
CREATE TABLE FRAME_DICTIONARY_TYPE(
			TypeID integer NOT NULL,
			KindID integer NOT NULL,
			TypeName varchar(200) NOT NULL,
			TypeDes varchar(1000) NULL,
			SortIndex integer NULL,
		 CONSTRAINT PK_FRAME_DICTIONARY_TYPE PRIMARY KEY  (TypeID,KindID)
	)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

insert into FRAME_DICTIONARY_KIND(KindID,KindName,KindDes,ParentID) values(1,'信息资源类型','信息资源类型',0);  
insert into FRAME_DICTIONARY_TYPE(TypeID,KindID,TypeName,TypeDes,SortIndex) values(1,1,'高速咨询','高速咨询',0);
insert into FRAME_DICTIONARY_TYPE(TypeID,KindID,TypeName,TypeDes,SortIndex) values(2,1,'商户广告','商户广告',1);
insert into FRAME_DICTIONARY_TYPE(TypeID,KindID,TypeName,TypeDes,SortIndex) values(3,1,'高速生活','高速生活',2);


CREATE TABLE FRAME_LOG_OPERATE(
			LogID varchar(32) NOT NULL,
			UserID varchar(32) NULL,
			UserName varchar(50) NULL,
			OperateModule varchar(250) NULL,
			OperateType varchar(50) NULL,
			LogDate TIMESTAMP NULL,
			LogDes LONGTEXT NULL,
		 CONSTRAINT PK_FRAME_LOG_OPERATE PRIMARY KEY  (LogID)
	)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

CREATE TABLE FRAME_LOG_SYSTEM(
			LogID varchar(32) NOT NULL,
			LogThread varchar(50) NULL,
			OperateModule varchar(200) NULL,
			LogLevel varchar(50) NULL,
			LogDate TIMESTAMP NULL,
			LogDes LONGTEXT NULL,
		 CONSTRAINT PK_FRAME_LOG_SYSTEM PRIMARY KEY (LogID)
	)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

CREATE TABLE FRAME_ORGSTRUC_ROLE(
			RoleID varchar(32) NOT NULL,
			RoleName varchar(50) NULL,
			RoleType integer NULL,
			OrgID varchar(50) NULL,
			Memo varchar(200) NULL,
		 CONSTRAINT PK_FRAME_ORGSTRUC_ROLE PRIMARY KEY  (RoleID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

CREATE TABLE FRAME_ORGSTRUC_OPERATOR(
			OperatorID varchar(50) NOT NULL,
			ModuleName varchar(200) NULL,
			OperatorName varchar(200) NULL,
			URLMapping varchar(200) NULL,
		 CONSTRAINT PK_FRAME_ORGSTRUC_OPERATOR PRIMARY KEY  (OperatorID)
		)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;
		 
CREATE TABLE FRAME_ORGSTRUC_ROLE_OPERATOR(
			RoleID varchar(32) NOT NULL,
			OperatorID varchar(32) NOT NULL,
		 CONSTRAINT PK_FRAME_ORGSTRUC_ROLE_OPERATO PRIMARY KEY (RoleID,OperatorID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

CREATE TABLE FRAME_ORGSTRUC_USER(
			UserID varchar(32) NOT NULL,
			Account varchar(50) NULL,
			Pwd varchar(50) NULL,
			UserName varchar(50) NULL,
			UserState integer NULL,
			Sex integer NULL,
			Type integer NULL,
			Position varchar(50) NULL,
			Mobile varchar(50) NULL,
			Telephone varchar(50) NULL,
			Email varchar(50) NULL,
			DefaultOrgID varchar(32) NULL,
			Memo varchar(200) NULL,
			lastModifyTime TIMESTAMP NULL,
			fileId varchar(32) NULL,
			point integer null,
			qq varchar(100) null,
			weixin varchar(100) NULL,
			weibo varchar(100) NULL,
			createTime TIMESTAMP,
		 CONSTRAINT PK_FRAME_ORGSTRUC_USER PRIMARY KEY  (UserID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;
alter table FRAME_ORGSTRUC_USER add column contactPerson varchar(200);
alter table FRAME_ORGSTRUC_USER add column contactOrgName varchar(200);

CREATE TABLE FRAME_ORGSTRUC_USER_ROLE(
			UserID varchar(32) NOT NULL,
			RoleID varchar(32) NOT NULL,
		 CONSTRAINT PK_FRAME_ORGSTRUC_USER_ROLE PRIMARY KEY  (UserID,RoleID)
);


CREATE TABLE CMSCONTENT(
      ID varchar(32) NOT NULL,
      CATEGORYID varchar(32) NULL,
      TITLE varchar(200) NULL,
      CONTENT LONGTEXT NULL,
      AUTHORID varchar(32) NULL,
      AUTHORNAME varchar(200) NULL,
      CRATETIME TIMESTAMP NULL,
     CONSTRAINT PK_CMSCONTENT PRIMARY KEY (ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

CREATE TABLE SCORELEVEL(
      LEVELKEY varchar(32) NOT NULL,
      LEVEDES varchar(50) NOT NULL,
      SCORE integer NULL,
      MEMO varchar(200) NULL,
     CONSTRAINT PK_LEVEL PRIMARY KEY (LEVELKEY)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

insert into SCORELEVEL(LEVELKEY,LEVEDES,SCORE,MEMO) values('REG','注册',100,'新注册用户奖励积分：100分');
insert into SCORELEVEL(LEVELKEY,LEVEDES,SCORE,MEMO) values('LOGIN','登陆',10,'账号登陆用户每次奖励积分：10分，每天奖励1次');
insert into SCORELEVEL(LEVELKEY,LEVEDES,SCORE,MEMO) values('SHARE','高速资讯分享',20,'每分享一次奖励积分：20分');
insert into SCORELEVEL(LEVELKEY,LEVEDES,SCORE,MEMO) values('BAOLIAO','爆料',20,'每报料一次奖励积分：20分，每天最多奖励3次');
insert into SCORELEVEL(LEVELKEY,LEVEDES,SCORE,MEMO) values('CMS','监控发布',40,'每次信息发布奖励积分40分');


CREATE TABLE USERSCOREDETAIL(
	ID varchar(32) NOT NULL,
	UserID varchar(32) NOT NULL,
	LEVELKEY varchar(32) NOT NULL,
	SCORE integer NULL,
	CRATETIME TIMESTAMP NULL
);

CREATE TABLE PRODUCT(
		ID varchar(32) NOT NULL,
		TITLE varchar(50) NULL,
		PROTYPE integer NOT NULL,
		fileId varchar(32) NULL,
		Score integer NOT NULL,
		Creator varchar(32) NULL,
		CreatorName varchar(200) NULL,
		CreateTime TIMESTAMP NULL,
	 CONSTRAINT PK_PRODUCT PRIMARY KEY (ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

CREATE TABLE USEREXCHNAGE(
		ID varchar(32) NOT NULL,
		USERID varchar(32) NOT NULL,
		PRODUCTID varchar(32) NOT NULL,
		PRODUCTTITLE varchar(50) NULL,
		STATUS integer NOT NULL,
		Score integer NOT NULL,
		HANDLER varchar(32) NULL,
		HANDLERName varchar(200) NULL,
		REICADDRESS varchar(500) NULL,
		REICPHONE varchar(50) NULL,
		CreateTime TIMESTAMP NULL,
	 CONSTRAINT PK_USEREXCHNAGE PRIMARY KEY (ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;
