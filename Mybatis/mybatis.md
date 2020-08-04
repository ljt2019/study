# in 与 exists

in 适合于外表大而内表小的情况，exists 适合于外表小而内表大的情况。但not时，无论那个表大，用not exists都比not in要快。

```sql
# 例如：表A（小表），表B（大表）


# 效率高，用到了B表上bb列的索引
select * from A where exists(select bb from B where bb=A.aa);
# 效率低，用到了A表上aa列的索引
select * from A where aa in (select bb from B);

# 效率高，用到了B表上bb列的索引
select * from B where bb in (select aa from A);
# 效率低，用到了A表上aa列的索引
select * from B where exists(select aa from A where aa=B.bb);

# 符合情况则返回true，不符合则返回false
SELECT exists(SELECT _view.id FROM mem_body_report_view _view WHERE _view.member_report_id = #{reportId} AND _view.delete_flag = 0);
```

# 条件运算符

1. case when

   ~~~sql
   (case when type_id = 'SALE' then '销售' when type_id = 'PURCHASE' then '采购' else '其他' end) as orderType;
   ~~~

2. if(expr1,expr2,expr3)与 decode()



【与我们常用的三目运算类似。expr1是一个表达式，如果true，返回expr2否则为expr3】

   【三种选择条件以上 case when】

	方式1：其中value=compare-value则返回result。
	case when _type.order_type_id = 'SALE' then '销售' when _type.order_type_id = 'PURCHASE' then '采购' else '其他' end as orderType;
	
	方式2:如果第一个条件为真，返回result。如果没有匹配的result值，那么结果在ELSE后的result被返回。如果没有ELSE部分，那么NULL被返回。 
	select case _type.order_type_id when 'SALE' then "销售" when 'PURCHASE' then "采购" else "其他" end as orderType;

3、【ifnull(expr1,expr2)，如果expr1不是null，ifnull()返回expr1，否则它返回expr2。ifnull()返回一个数字或字符串值，取决于它被使用的上下文环境。】

4、【concat(str1,str2,...) 如有任何一个参数为null，则返回值为 null】【concat_ws(str1,str2,...) 函数会忽略任何分隔符参数后的 null 值】
		
5、【group_concat([distinct] 要连接的字段 [order by asc/desc 排序字段] [separator '分隔符'])】

	连接起来的字段如果是int型，一定要转换成char再拼起来，否则在你执行后返回的将不是一个逗号隔开的串，而是byte[]。
	select group_concat(CAST(id as char)) from t_dep 返回逗号隔开的串
	select group_concat(Convert(id , char)) from t_dep 返回逗号隔开的串  

6、模糊搜索【注意：数字和字符串连接应该用CONCAT()】
		<if test="param.deviceIdentity != null and param.deviceIdentity != '' ">
			AND device.device_identity LIKE CONCAT('%', #{param.deviceIdentity}, '%')
		</if>
		//全模糊查询,效率更高
		<if test="mallId !=null and mallId !='' ">
			AND instr(mall_id,#{mallId}) > 0
	    </if>

判断字符是否相等		
  <if test="grade!= null and grade!= '' and grade == '1'.toString()">
      id = ''
  </if>

7、两时间之差，返回SECOND数【select TIMESTAMPDIFF(SECOND,start_time,end_time)】
		
7、格式化时间【date_format(_re.time,'%Y-%m-%d %H:%i:%s') as timeStr】 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")记得时区转换

8、【STRCMP(str1, str2):比较两个字符串，如果这两个字符串相等返回0，如果第一个参数是根据当前的排序小于第二个参数顺序返回-1，否则返回1。】
		
8、依据时间进行分组，并按时间进行降序排序【分组查询】
	select t.* from
		(select r.mall_id
		,sum(r.sport_metre) as sportMetre
		,sum(r.sport_duration) as sportDuration
		,date_format_(r.create_time,'%Y-%m-%d') as createDate 
		from mem_sport_record r where r.mall_id=#{mallId} and r.create_time>=#{startTime} and r.create_time<=#{endTime} group by createDate) t 
	order by t.createDate asc;	

9、判空集合是否为空：【!param.statuses.isEmpty()】，遍历
	<if test="statuses != null and !statuses.isEmpty()">
         and da.status in
        <foreach item="item" index="index" collection="statuses" open="(" separator="," close=")">
          #{item}
        </foreach>
    </if>
	
10、【省市区关联】
		LEFT JOIN pub_region province ON province.id = xx.provinceId
		LEFT JOIN pub_region city ON city.id = xx.cityId
		LEFT JOIN pub_region region ON region.id = xx.regionId

11、返回集合list,list集合里边嵌套list集合
	//对应的OutDTO：GymDeviceOutDTO属性如下
	private String gymId;
	private String address;
	private String gymName;
	private String gymType;
	//......
	private List<GymDeviceDetailOutDTO> gymDeviceList = new ArrayList<GymDeviceDetailOutDTO>();

   1)、首先写一个 resultMap【返回list集合就需要写这个resultMap】
	<!-- 根据场馆查询场馆设备结果集 -->
	<resultMap id="GymRegionOutDTOMap" type="cn.healthmall.sail.base.dto.GymDeviceOutDTO"> 
		<id column="gymId" property="gymId"/>
		<result column="gymName" property="gymName"/>
		<result column="address" property="address"/>
		<result column="gymType" property="gymType"/>
		//......
		<collection property="gymDeviceList" javaType="ArrayList" 
			ofType="cn.healthmall.sail.base.dto.GymDeviceDetailOutDTO" column="id">
			<result column="id" property="id"/>
			<result column="deviceName" property="deviceName"/>
			<result column="devicePrice" property="devicePrice"/>
			<result column="deviceIdentity" property="deviceIdentity"/>
			<result column="productId" property="productId"/>
			<result column="deviceNo" property="deviceNo"/>
			<result column="productInfoId" property="productInfoId"/>
		</collection>
	</resultMap>
	
   2)、接着 
	<select id="queryGymDeriveList" resultMap="GymRegionOutDTOMap" parameterType="cn.healthmall.sail.base.dto.GymInDTO">
		SELECT temp.* from (
			SELECT 
				gym.id AS gymId 
				,gym.name as gymName
				,gym.address as address
				,CASE  
				WHEN gym.type = 0 then '小象运动'
				WHEN gym.type = 1 then '生活馆'
				END as gymType
				,dev.device_identity AS deviceIdentity
				,dev.device_no AS deviceNo
				,dev.product_info_id as productInfoId
				,dev.id as id
				,product.name as deviceName
				,product.id as productId
			FROM dev_gym gym
			LEFT JOIN dev_device dev ON dev.gym_id = gym.id AND dev.delete_flag = 0
			LEFT JOIN dev_product product ON dev.product_info_id = product.id
			LEFT JOIN dev_device_price gymPrice ON gymPrice.product_id = dev.product_info_id AND gymPrice.delete_flag = 0 
			AND gymPrice.device_scope_type = 3 AND gymPrice.device_scope = gym.id
			LEFT JOIN dev_device_price cityPrice ON cityPrice.product_id = dev.product_info_id AND cityPrice.delete_flag = 0 
			AND cityPrice.device_scope_type = 2 AND cityPrice.device_scope = gym.cityId
			LEFT JOIN dev_device_price allPrice ON allPrice.product_id = dev.product_info_id AND allPrice.delete_flag = 0 
			AND allPrice.device_scope_type = 1 AND allPrice.device_scope = '100000'
			WHERE gym.id = #{InDTO.id}
			AND gym.delete_flag = 0
			) temp
		ORDER BY temp.deviceNo
	</select>
	
两个例子：
	<!-- 获取省份信息列表 注意：钓鱼岛下边没有市区，没有查出 -->
	<resultMap id="RegionInfoOutDTOMap"
		type="cn.healthmall.sail.activity.dto.CityListOutDTO">
		<id column="provinceId" property="provinceId" />
		<result column="provinceName" property="provinceName" />
		<collection property="cities" javaType="ArrayList"
			ofType="cn.healthmall.sail.activity.dto.CityOutDTO" column="cityId">
			<result column="cityId" property="cityId" />
			<result column="cityName" property="cityName" />
		</collection>
	</resultMap>
	<select id="getRegionInfo" resultMap="RegionInfoOutDTOMap">
		SELECT
		_city.id AS cityId,
		_city.name AS cityName,
		_region.id AS provinceId,
		_region.name AS provinceName
		FROM
		pub_region _city
		INNER JOIN pub_region _region ON _region.id = _city.parent_id
		AND _region.level_type = 1
	</select>
	
	<!-- 获取场馆列表 传入经纬度（城市id） -->
	<resultMap id="GymListOutDTOMap" type="cn.healthmall.sail.activity.dto.GymListOutDTO">
		<id column="regionId" property="regionId" />
		<result column="regionName" property="regionName" />
		<collection property="infos" javaType="ArrayList"
			ofType="cn.healthmall.sail.activity.dto.GymInfoOutDTO" column="gymId">
			<result column="gymId" property="gymId" />
			<result column="gymName" property="gymName" />
			<result column="isSpecialGym" property="isSpecialGym" />
			<result column="smallIcon" property="smallIcon" />
			<result column="normalIcon" property="normalIcon" />
		</collection>
	</resultMap>
	<select id="getGymInfo" resultMap="GymListOutDTOMap">
		SELECT
		_gym.id AS gymId,
		_gym.name AS gymName,
		_gym.type AS isSpecialGym,
		_gym.small_icon AS smallIcon,
		_gym.normal_icon AS normalIcon,
		_region.name AS regionName,
		_region.id AS regionId
		FROM
		base_gym _gym
		LEFT JOIN pub_region _city ON _gym.city_id = _city.id
		LEFT JOIN
		pub_region _region ON _gym.region_id = _region.id
		WHERE
		_city.id = #{cityId}
		AND _gym.delete_flag = 0
		ORDER BY _gym.name
	</select>

12、返回txt文本信息【带With,在xml中】：List<Medal> selectByExampleWithBLOBs(MedalExample example);

13、【union all】的用法，两个表字段顺序要一致
	<select id="list" parameterType="java.lang.String" resultType="cn.healthmall.sail.member.dto.ArSportRecordOutDTO">
		SELECT a.* from (
			SELECT
				_record.id AS id,
				date_format(_record.start_time,'%Y-%m-%d %H:%i:%s') AS startTimeStr,
				_record.sport_duration AS sportDuration,
				_record.energy_consumption AS energyConsumption,
				_record.achievement AS achievement,
				_record.device_id AS deviceId,
				_record.course_id AS courseId,
				top.name AS courseName,
				top.image_url AS frontCoverUrl,
				'' AS trainer,
				_record.start_time AS startTime,
				_record.end_time AS endTime,
				_record.member_id AS memberId
				FROM mem_ar_record _record
				JOIN pre_top_motion top
				ON _record.course_id =top.id
				WHERE
				#{memberId}=_record.member_id
				AND _record.delete_flag=0
				AND top.delete_flag=0
		UNION ALL		
			SELECT
				_record.id AS id,
				date_format(_record.start_time,'%Y-%m-%d %H:%i:%s') AS startTimeStr,
				_record.sport_duration AS sportDuration,
				_record.energy_consumption AS energyConsumption,
				_record.achievement AS achievement,
				_record.device_id AS deviceId,
				_record.course_id AS courseId,
				_course.name AS courseName,
				_course.front_cover_url AS frontCoverUrl,
				_course.trainer AS  trainer,
				_record.start_time AS startTime,
				_record.end_time AS endTime,
				_record.member_id AS memberId
				FROM mem_ar_record _record
				JOIN base_ar_course _course
				ON _record.course_id =_course.id
				WHERE
				#{memberId}=_record.member_id
				AND _record.delete_flag=0
				AND _course.delete_flag=0
				) AS a
		ORDER BY a.startTime DESC
	</select>
	
15、inert into [key存在的就报错] / replace into [key存在的就更新]

16、创建索引：【create unique index idx_sys_users_username on sys_users(username);】，频繁查询的字段创建索引来提交性能。

17、布尔类型：available bool default false, <-sql-> `available` tinyint(1) DEFAULT '0', <-实体类-> private Boolean available = Boolean.FALSE;

18、在查询语句中使用参数：【 #{unPassPublicElectiveCourse} as temp 】

19、参数列表：List<ScoreSubItemVo> scoreItem(@Param("scoreJobsCode") String scoreJobsCode,@Param("semesterId"));

20、test中比较数值大小
  <if test="credit != null and credit &gt; 0">
      XF = #{credit},
  </if>

--聚合查询
 <!-- 班级成绩导出（学生课程成绩信息） add by linjitai on 20200115-->
 <resultMap id="classScoreMap" type="com.ly.education.score.api.vo.ClassScoreExportVo"
            extends="classScoreExportBaseMap">

     <!-- 表头 -->
     <collection property="classCourseInfoVoList" resultMap="classCourseInfoMap">
     </collection>
     
     <!-- 表体 -->
     <collection property="classStudentScoreInfoVoList" resultMap="classStudentScoreInfoMap">
     </collection>

 </resultMap>

--聚合查询
 <resultMap id="QueryTrainCenterMap"
            type="com.ly.education.train.manage.api.vo.TrainCenterVo" extends="BaseResultMap">
     <result column="departName" property="departName" jdbcType="VARCHAR"/>
     <result column="leaderName" property="leaderName" jdbcType="VARCHAR"/>
     <result column="laboratoryCnt" property="laboratoryCnt" jdbcType="VARCHAR"/>
     <collection property="courseIdList" ofType="string"
                 select="selectCourseIdList" column="trainCenterId = SXZXBH">
     </collection>
     <collection property="majorIdList" ofType="string"
                 select="selectMajorIdList" column="trainCenterId = SXZXBH">
     </collection>
 </resultMap>


       SUM(NVL(XSCJHZB.XF, 0)) OVER(PARTITION BY XSCJHZB.XN, XSCJHZB.XH) AS QDZXF,
       SUM(NVL(XSCJHZB.KCXF, 0)) OVER(PARTITION BY XSCJHZB.XN, XSCJHZB.XH) AS YDZXF,
       SUM(NVL(XSCJHZB.XF, 0)) OVER(PARTITION BY XSCJHZB.XN,XSCJHZB.XQ, XSCJHZB.XH) AS QDZXFXNXQ,
       SUM(NVL(XSCJHZB.KCXF, 0)) OVER(PARTITION BY XSCJHZB.XN,XSCJHZB.XQ, XSCJHZB.XH) AS YDZXFXNXQ,

### 查询条件中字符比较 

~~~sql
           <if test="isSupplierCode != null '1'.toString()==isSupplierCode">
              and  GYSDM is not null
              and DGZT in ('0','1')
            </if>
~~~















