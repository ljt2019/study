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

   【三种选择条件以上 case when】

	方式1：其中value=compare-value则返回result。
	case when _type.order_type_id = 'SALE' then '销售' when _type.order_type_id = 'PURCHASE' then '采购' else '其他' end as orderType;
	
	方式2:如果第一个条件为真，返回result。如果没有匹配的result值，那么结果在ELSE后的result被返回。如果没有ELSE部分，那么NULL被返回。 
	select case _type.order_type_id when 'SALE' then "销售" when 'PURCHASE' then "采购" else "其他" end as orderType;

3、【mysql ,ifnull(expr1,expr2)，如果expr1不是null，ifnull()返回expr1，否则它返回expr2。ifnull()返回一个数字或字符串值，取决于它被使用的上下文环境。】

4、【concat(str1,str2,...) 如有任何一个参数为null，则返回值为 null】【concat_ws(str1,str2,...) 函数会忽略任何分隔符参数后的 null 值】
		
5、【group_concat([distinct] 要连接的字段 [order by asc/desc 排序字段] [separator '分隔符'])】

	连接起来的字段如果是int型，一定要转换成char再拼起来，否则在你执行后返回的将不是一个逗号隔开的串，而是byte[]。
	select group_concat(CAST(id as char)) from t_dep 返回逗号隔开的串
	select group_concat(Convert(id , char)) from t_dep 返回逗号隔开的串  

# 模糊查询

~~~sql
 <if test="mallId !=null and mallId !='' ">
 	AND instr(mall_id,#{mallId}) > 0
 </if>
~~~

# test中比较字符是否相等


~~~sql
<if test="grade!= null and grade!= '' and grade == '1'.toString()">
     
</if>
~~~

# test中比较数值大小
~~~sql
<if test="credit != null and credit &gt; 0">
    XF = #{credit},
</if>
~~~

# 比较两个字符串



8、【STRCMP(str1, str2):，如果这两个字符串相等返回0，如果第一个参数是根据当前的排序小于第二个参数顺序返回-1，否则返回1。】
		
8、依据时间进行分组，并按时间进行降序排序【分组查询】
	select t.* from
		(select r.mall_id
		,sum(r.sport_metre) as sportMetre
		,sum(r.sport_duration) as sportDuration
		,date_format_(r.create_time,'%Y-%m-%d') as createDate 
		from mem_sport_record r where r.mall_id=#{mallId} and r.create_time>=#{startTime} and r.create_time<=#{endTime} group by createDate) t 
	order by t.createDate asc;	

# 聚合

## 通过子查询

~~~sql
--一个参数
<resultMap id="AssociationMapper" type="com.ly.cloud.base.dto.DomainDTO">
	<!-- WARNING - @mbg.generated -->
	<id column="BH" property="bh" jdbcType="VARCHAR" />
	<result column="MC" property="mc" jdbcType="VARCHAR" />
	<result column="MS" property="ms" jdbcType="VARCHAR" />
	<result column="PXH" property="pxh" jdbcType="DECIMAL" />
	<collection property="servingList" column="bh"
		select="com.ly.cloud.base.mapper.ServingDTOMapper.selectByDomainId"></collection>
</resultMap>
--多个参数
<resultMap id="AssociationMapper" type="com.ly.cloud.base.dto.ServingDTO">
	<id column="BH" property="bh" jdbcType="VARCHAR" />
	<result column="MC" property="mc" jdbcType="VARCHAR" />
	<result column="MS" property="ms" jdbcType="VARCHAR" />
	<result column="PXH" property="pxh" jdbcType="DECIMAL" />
	<result column="SSY" property="ssy" jdbcType="VARCHAR" />
	<collection property="permissionList" column="ybh=ssy,fwbh=bh"
		select="com.ly.cloud.base.mapper.PermissionDTOMapper.selectPermissionList"></collection>
</resultMap>
~~~

##  SUM(NVL(XSCJHZB.XF, 0)) OVER(PARTITION BY XSCJHZB.XN, XSCJHZB.XH) AS QDZXF,

~~~sql
  <resultMap id="classScoreMap" type="com.ly.education.score.api.vo.ClassScoreExportVo"
            extends="classScoreExportBaseMap">
  	<!-- 表头 -->
  	<collection property="classCourseInfoVoList" resultMap="classCourseInfoMap">
 	</collection>
 
 	<!-- 表体 -->
 	<collection property="classStudentScoreInfoVoList" resultMap="classStudentScoreInfoMap">
 	</collection>
  </resultMap>
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
~~~

# 批量插入

~~~sql
<insert id="batchInsert"
        parameterType="java.util.List">
    INSERT INTO T_BSGL_BSKT_MXZY (
        BSKT_ID,
        NJZYFX_ID,
        CJR,
        CJSJ
    )
    <foreach collection="list" item="item" index="index" separator="UNION ALL" open="(" close=")">
        SELECT
            #{item.graduationTopicId,jdbcType=VARCHAR},
            #{item.gradeMajorId,jdbcType=VARCHAR},
            #{item.creator,jdbcType=VARCHAR},
            #{item.createTime,jdbcType=DATE}
        FROM dual
    </foreach>
</insert>
~~~

~~~sql
    <insert id="initMajorDevelopLink" >
        insert into T_PYFA_ZYPYHJSZ (
		ZYPYHJSZ_ID,
		ND,
		DW_ID,
		ZY_ID,
        XSLBM,
		ZHXGSJ
        )
        SELECT DISTINCT
        ZYPYFX.NJ||ZYPYFX.DW_ID||ZYPYFX.ZY_ID||ZYPYFX.XSLBM,
        nvl(ZYPYFX.NJ,'-') NJ,
        nvl(ZYPYFX.DW_ID,'-') DW_ID,
        ZYPYFX.ZY_ID,
        nvl(ZYPYFX.XSLBM,'-') XSLBM,
        sysdate
		FROM ly_yjs_hxsj.T_PYFA_ZYPYFX ZYPYFX
		WHERE ZYPYFX.ZYPYFX_ID in
            <foreach item="item" index="index" collection="majorDevelopDirectionIdList" open="(" separator="," close=")">
                #{item}
            </foreach>
        and NOT EXISTS (
        SELECT 1 FROM T_PYFA_ZYPYHJSZ ZYPYHJSZ
        WHERE ZYPYHJSZ.ZYPYHJSZ_ID=ZYPYFX.nj||ZYPYFX.Dw_Id||ZYPYFX.ZY_ID||ZYPYFX.XSLBM
        )
    </insert>
~~~

~~~sql
<insert id="batchInsert">
        MERGE into ly_yjs_xtgl.T_XTGL_FJ FJ
        USING (<foreach item="item" index="index" collection="list" open="(" close=")" separator="union all">
            select
            #{item.uid,jdbcType=VARCHAR} as FJ_ID,
            #{item.name,jdbcType=VARCHAR} as WJMC,
            #{item.type,jdbcType=VARCHAR} as WJLX,
            #{item.filePath,jdbcType=VARCHAR} as WJ_ID,
            #{item.businessId,jdbcType=VARCHAR} as SJ_ID,
            #{item.businessTable,jdbcType=VARCHAR} as SSB,
            #{item.size,jdbcType=NUMERIC} as WJDX,
            #{item.creator,jdbcType=VARCHAR} as CJR,
            #{item.createTime,jdbcType=TIMESTAMP} as CJSJ,
            #{item.editor,jdbcType=VARCHAR} as ZHXGR,
            #{item.editTime,jdbcType=TIMESTAMP} as ZHXGSJ
            from dual
    </foreach>)tr
        ON (FJ.WJLX = tr.WJLX AND FJ.SJ_ID = tr.SJ_ID AND FJ.WJ_ID = tr.WJ_ID )
        WHEN NOT MATCHED THEN
        insert  (
        FJ.FJ_ID,
        FJ.WJMC,
        FJ.WJLX,
        FJ.WJ_ID,
        FJ.SJ_ID,
        FJ.SSB,
        FJ.WJDX,
        FJ.CJR,
        FJ.CJSJ,
        FJ.ZHXGR,
        FJ.ZHXGSJ
        )values
        (
        tr.FJ_ID,
        tr.WJMC,
        tr.WJLX,
        tr.WJ_ID,
        tr.SJ_ID,
        tr.SSB,
        tr.WJDX,
        tr.CJR,
        tr.CJSJ,
        tr.ZHXGR,
        tr.ZHXGSJ
        )

    </insert>
~~~

~~~sql

    <insert id="insertColleges" parameterType="com.ly.education.cultivationprocess.server.entity.ProjectInfoCollegeEntity" >
        insert all
        <foreach collection="list" item="item">
            into T_PYGC_XMXX_XY(xmxxid, xy, sbzt) values (#{item.projectInfoId}, #{item.collegeName}, #{item.status})
        </foreach>
        Select 1 from dual
    </insert>
~~~



# 遍历

```sql
<if test="workloadIdList != null and !workloadIdList.isEmpty()">
     and workloadId in
    <foreach item="item" index="index" collection="workloadIdList" open="(" separator="," close=")">
      #{item}
    </foreach>
</if>	
```
# 省市区关联

~~~sql
LEFT JOIN pub_region province ON province.id = xx.provinceId
LEFT JOIN pub_region city ON city.id = xx.cityId
LEFT JOIN pub_region region ON region.id = xx.regionId
~~~

~~~java
   //返回集合list,list集合里边嵌套list集合
   //对应的OutDTO：GymDeviceOutDTO属性如下
	private String gymId;
	private String address;
	private String gymName;
	private String gymType;
	//......
	private List<GymDeviceDetailOutDTO> gymDeviceList = new ArrayList<GymDeviceDetailOutDTO>();
	
~~~

   1)、首先写一个 resultMap【返回list集合就需要写这个resultMap】

~~~
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
	
~~~

12、返回txt文本信息【带With,在xml中】：List<Medal> selectByExampleWithBLOBs(MedalExample example);

# 【union all】的用法，两个表字段顺序要一致

~~~
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
~~~



15、inert into [key存在的就报错] / replace into [key存在的就更新]

17、布尔类型：available bool default false, <-sql-> `available` tinyint(1) DEFAULT '0', <-实体类-> private Boolean available = Boolean.FALSE;



### 查询条件中字符比较 

~~~sql
<if test="isSupplierCode != null '1'.toString()==isSupplierCode">
   and  GYSDM is not null
   and DGZT in ('0','1')
</if>
~~~



~~~
        <choose>
            <when test="emptyType == 'batch'.toString()">
                jxzrw_id in
                <foreach collection="teachingClassChildrenIds" index="index" item="item" close=")" open="(" separator=",">
                    #{item,jdbcType=VARCHAR}
                </foreach>
            </when>
            <when test="emptyType == 'alone'.toString()">
                jsrkap_id = #{classOpenArrangeId,jdbcType=VARCHAR}
            </when>
            <otherwise>
                1 = 2
            </otherwise>
        </choose>
~~~













