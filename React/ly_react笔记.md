# 单选框

~~~javascript
            {
                title: '排课类型', dataIndex: 'arrangeType', key: 'arrangeType', align: "center",
                width: 200,
                render: (text, record, index) => {
                    return <RadioGroup defaultValue={record.arrangeType} onChange={(e) => {
                        record.arrangeType = e.target.value;
                        this.state.dataSource[index] = record;
                        this.setState({ dataSource: this.state.dataSource });
                        this.state.onChangeRows[index] = record;
                    }}>
                        <Radio value={'0'}>只排</Radio>
                        <Radio value={'1'}>不可排</Radio>
                    </RadioGroup>
                }
            },
~~~

# # 预览PDF文件

~~~js
npm install react-pdf-js
~~~

# json与对象之间的转换

~~~js
//对象 --> json
let obj = JSON.stringify(jsonStr); 
//json --> 对象
let jsonStr = JSON.parse(obj); 
~~~

7、时间格式化显示：new Date(Date.parse(${dataSource[i].events[0].timesStartTime}.replace(/-/g, "/"))).format('hh:mm'))

# 排序

~~~js
  const newSingleSchudle = JSON.parse(JSON.stringify(singleSchedule)).sort(
    (a, b) => {
      // 以time为主排序，key为次排序
      if (a.time === b.time) {
        return a.key - b.key;
      } else {
        return a.time - b.time;
      }
    }
  );
~~~

# 显示里修改逻辑

~~~js
      {
        title: "在校状态",
        dataIndex: "sudentStatus",
        key: "sudentStatus",
        align: "center",
        width: 100,
        render: (text) => {
          let state = '';
          if (text == "1") {
            state = "在校";
          } else {
            state = "不在校";
          }
          return <div>{state}</div>
        }
      }
~~~

# 引入公共接口

~~~js
import { selectDepartment } from "../../../../../config/commonService"
~~~

# 滚动条

~~~js
import { Scrollbars } from "components";
<Scrollbars style={{ height: 400 }}>  </Scrollbars>
~~~

# 遍历数组

~~~js
  _this.state.selectedRows.map((item, i) => {
       let status = item.orderStatus;
       if (status != 0 && status != 1) {
           cnt++;
       }
   })
~~~

## 遍历设值

~~~
dataSource: data.map((item) => ({ developLinkCode: item.code, developLinkName: item.name ,developLinkType:'1'}))
~~~

# 父组件获取子组件表单值，获取表单

~~~js
//方式1
<SupplierInfo ref="supplierInfo "  record={this.state.record}  />
 //方式2
<SupplierInfo wrappedComponentRef={(inst) => (this.supplierInfo = inst)} record={this.state.record}/>
~~~

# 父组件获取子组件state值，获取页面值

~~~js
<SupplierInfo ref={(ref)=>{this.supplierInfo=ref}} record={this.state.record} />
//父组件可以这样使用子组件传来的参数
const supplierIds = this.supplierInfo.state.selectedRowKeys;
//子组件获取父组件值
this.props.record
~~~

# 父组件获取子组件***表单和state值以及方法***

~~~js
<AddModal ref="addModal" wrappedComponentRef={(inst) => (this.addModal = inst)} />
//取值
let courseIdList = this.addModal.state.selectedRowKeys

~~~

# 当前页面表单数据操作

~~~js
//给表单属性设值
this.props.form.setFieldsValue({ majorCreditList: this.MajorCreditModal.state.selectedRows });
//获取单个表单输入值
this.props.form.getFieldValue("majorCreditList")
// 注册validateFields获取【整个表单输入值】并且校验是否输入
 const { validateFields } = this.props.form; 
 //this.props.form.validateFields((err, values) => {
 validateFields((err, formData) => {
     if (!err) {
         
     }
 });
//未知？
const { getFieldDecorator,getFieldValue } = this.props.form

let courseAttributeCode = this.refs.modifyCourseAttributeModal.getFieldValue("courseAttributeCode")
~~~

# 获取当前搜索框的值

~~~js
 this.refs.searchModal.validateFields((err, formData) => {
     if (!err) {
         let grade = formData.grade
     }
 });
<SearchModal ref="searchModal" onSearch={this.onSearch} openChange={() => this.openChange()} />
<SearchModal wrappedComponentRef={(inst) => (this.searchModal = inst)} ref="searchModal" onSearch={this.onSearch}  />

~~~



# 表单组件，@Form.create()

~~~js
@Form.create()
export default class Approval extends React.Component {}
~~~

# 确认提示框

~~~js
approvalBooks = () => {
      let _this = this;
      confirm({
          title: '提示',
          content: '确认要提交吗？',
          onOk() {
              let _data = {
                  "orderNumberList": _this.state.selectedRowKeys,
                  "approvalStatus": 2,//修改审批状态,直接通过
                  "teachingClassNumber": _this.props.record.teachingClassNumber
              }
              updateOrderBook(_data, "orderBook:update", () => {
                  _this.indexQuery();
              });
          }
      });
  };
~~~

# 创建对象map并赋值

~~~js
//方式1
record.studentOrderCnt = value.target.value;
//方式2
let applyMap = {}
record['id'] = item.studentLinkRecordId
this.state.selectedRows.map((item) => {
    applyMap[item.applyName] = item.studentId
})
~~~

# 跳转页面，从URL参数中获取父页面传来的参数

~~~js
this.setState({
      enterWareNumber: this.props.match.params.enterWareNumber
})
~~~

# 设置表格行联合主键

~~~js
 rowKey: (record) => { return JSON.stringify({ teachingClassNumber: record.teachingClassNumber, classCode: record.classCode }) },
~~~

# 列宽设置

~~~js
import TableUtil from "src/utils/TableUtil";
import TableUtil from '@/utils/TableUtil';
scroll: { x: TableUtil.calculateColumsWidthSum(columns, 0) },
~~~

# 设置样式

~~~js
 style={{
    marginLeft: '-4px',
    color: '#1890ff',
    fontSize: '8px',
    textDecoration: 'none'
 }}
<Input style={{ height: 35.016, width: '246px' }} />
~~~

# 判空

~~~js
    if (typeof remark != "undefined" && remark != null && remark.replace(/(^s*)|(s*$)/g, "").length != 0) {
          disabled = true
    }
~~~

# 传id查详情

~~~js
isExtraQuery: id => `${base}/studentExtraScore/queryIsExtraData?studentExtraScoreId=${id}`
~~~

# 删除素组元素，添加数组元素

~~~js
let list=[]
// 删除
list.splice(index, 1)
// 添加
list.push(newState.inputValue)  
~~~

# 所在部门和开课单位

~~~js
import { selectDepartment, departmentSelectList } from "app/config/commonService";
//开课院系
  getDepartmentSelectList = () => {
    departmentSelectList((data) => {
      this.setState({
        departmentList: data
      });
    });
  };

selectDepartment({}, (data) => {
      this.setState({
        dwList: this.returnTreeData(data)
      })
    })

  returnTreeData = (data) => {
    return data.map((item) => {
      let child = [];
      if (item.child) {
        child = this.returnTreeData(item.child);
      }
      return { title: item.dwmc, key: item.dwh, value: item.dwh, children: child };
    });
  };

//---------
<Col {...colSpan}>
  <FormItem {...formItemLayout} label="所在部门">
    {getFieldDecorator("departmentId", {})
      (
        <TreeSelect
          allowClear
          placeholder="所在部门"
          dropdownStyle={{
            maxHeight: 300,
            overflow: "auto"
          }}
          treeData={this.state.dwList} />
      )}
  </FormItem>
</Col>
<Col {...colSpan}>
  <FormItem {...formItemLayout} label="开课部门">
    {getFieldDecorator("collegeId")(
      <Select placeholder="请选择" allowClear showSearch optionFilterProp="children">
        {this.state.departmentList.map((item) => {
          return <Option key={item.dwh}>{item.dwmc}</Option>;
        })}
      </Select>
    )}
  </FormItem>
</Col>
~~~

# 列隐藏

~~~js
ellipsis: true,
~~~

# 获取当前登陆用户

~~~js
import Cookies from "js-cookie"
  // userName={this.user.userName} //编号
  // name={this.user.name} //名称
  user = JSON.parse(Cookies.get("user") || "{}")
  newFormData1.proposerName = JSON.parse(Cookies.get('user') || '{}').name;
  newFormData1.proposerCode = JSON.parse(Cookies.get('user') || '{}').userName;
~~~

# 获取UUID，附件id

~~~
import { generatorUUIDString } from "src/app/config/commonService"
~~~

# 下拉框获取到值和代码 代码集

~~~js
//获取数据
let factor = option.props.data.scorePercentileValue;

//下拉框
let enablSelect = <Select
        showSearch
        style={{ width: "90%" }}
        placeholder=""
        optionFilterProp="children"
        onChange={(v, option) => this.onSelectChangePositive(v, record, option, index)}
        defaultValue={this.displayScore(text, _scoreHierarchyCode)}
        allowClear={true}
        disabled={disabled}
    >
        {
            this.state.scoreValueList.map((res) =>
                <Option key={res.scoreValueCode} data={res}>{res.scoreValueName}</Option>
            )
        }
    </Select>
~~~

# 自定义 导出 文件调用

~~~js
 import AxiosHelperUtil from "src/utils/AxiosHelperUtil";
   //学籍卡导出pdf add by linjitai on 21300708
  doPrintPdf = () => {
    this.props.form.validateFields((err, formData) => {
      if (!err) {
        this.setState({
          exportLoading: true,
        })
        const timeout = 150000;
        formData.xhList = this.state.selectedStudentNames.map((item, index) => {
          return item.key;
        });
		AxiosHelperUtil.downloadFile(studentCard.printStudentCardPdf, formData, "studentCard:printPdf" , 150000);
      }
    });
  };
~~~

# 文件下载

```js
 import AxiosHelperUtil from "src/utils/AxiosHelperUtil";
static downloadFile = (url, params, permission, timeout) => {

downloadFile(url, params, {
  timeout,
  showTimeout: true,
  headers: { permission: permission }
}).then((response) => {
  if (response.status == "200") {
    // 创建隐藏的a标签
    const aLink = document.createElement("a");
    document.body.appendChild(aLink);
    aLink.style.display = "none";
    // 设置下载内容
    const objectUrl = URL.createObjectURL(response.data);
    aLink.href = objectUrl;
    // 设置下载文件名
    const fileName = response.headers["content-disposition"].split(";")[1].split("filename=")[1];
    aLink.download = decodeURI(fileName);
    // 下载
    aLink.click();
    // 移出标签
    document.body.removeChild(aLink);
  } else {
    //将字符串转换成 Blob对象
    var blob = response.data;
    //将Blob 对象转换成字符串
    var reader = new FileReader();
    reader.readAsText(blob, "utf-8");
    reader.onload = function (e) {
      var obj = JSON.parse(reader.result);
      message.error("下载失败:" + obj.message);
    };
  }
});
};
  
```
```js
export function downloadFile(url, params, config = {}) {

return new Promise((resolve, reject) => {
  axios
    .post(url, params, { responseType: "blob", ...config })
    .then(res => {
      resolve(res);
    })
    .catch(err => {
      reject(err.data);
    });
});
  }
```
# 搜索传参

~~~js
  // 搜索 
    onSearch = (params) => {
        this.params.pageNo = 1;
        this.params = { ...this.params, ...params };
        params.param.analyType = '1'
        params.param.classNo = this.props.classNo
        this.setState({
            searchParam: params.param
        })
        this.indexQuery();
    };
~~~

# 公共组件 ，导出

~~~js
import ExportModal from "src/utils/exportModal";

 {/* 导出模态框 */}
 <DragModal
     className="md-md"
     title="导出"
     destroyOnClose
     maskClosable={false}
     visible={this.state.exportVisible}
     onCancel={this.showExportModal}
     footer={[
         <Button key={2} onClick={() => { this.showExportModal(); this.exportModalState(false); }} >
             取消
         </Button>,
         <Button key={1} disabled={this.state.exportLoading} type="primary" onClick={() => { this.exportModal.exportSubmit(); }} >
             确定
         </Button>
     ]}
 >
     <Spin spinning={this.state.exportLoading} tip="处理中，请稍等...(若提示处理失败或等待时间过长，请重试)">
         <ExportModal
             dataSource={columns}
             ref="exportModal"
             showExportModal={this.showExportModal}
             exportModalState={this.exportModalState}
             exportExcelUrl={projectInfoChangeApplyApi.export}
             selectedRows={this.state.selectedRows}
             params={this.params}
             permission={authority.export}
             excelName="项目变更信息"
             timeout={15000}
             initSet={0}
             disabledSet={false}
             wrappedComponentRef={(inst) => (this.exportModal = inst)} />
     </Spin>
 </DragModal>
~~~



# 公共组件，导入

~~~js
import ImportModal from "src/utils/importModal";

{/* 导入文件模态框 */}
<DragModal
    className="md-sm"
    title="导入文件"
    destroyOnClose
    maskClosable={false}
    visible={this.state.importVisible}
    onCancel={this.closeImportModal}
    footer={[
        <Button key={2} onClick={this.closeImportModal}>
            返回
        </Button>
    ]}
>
    <ImportModal ref="importModal"
        onSearch={this.onSearch}
        downloadTemplateUrl={doctorNamesApi.exportTemplate}
        importDataUrl={doctorNamesApi.importData}
        permission={authority.importData}
        timeout={150000}
    />
</DragModal>
~~~



# 判断表单的值是否改变

~~~js
export const isFormChange = (form, oldValues) => {
const { getFieldsValue } = form;
const newValue = getFieldsValue();
const keys = Object.keys(newValue);
let isChange = false;
// 遍历表单的key
for (let index = 0; index < keys.length; index++) {
const item = keys[index];
if (item && oldValues[item] === null && newValue[item] === "") {
  break;
}
if (item && newValue[item] != oldValues[item]) {
  isChange = true;
  break;
}
}
return isChange;
};
~~~

# 前开课学年学期

~~~js
    getNowOpenTime = (param) => {
        semesterNow((data) => {
            this.setState(
                {
                    semester: data.semester
                },
                () => {
                    this.onSearch()
                }
            )
        })
    }
~~~

# 数据行中添加弹出框

~~~js
{
  title: "可用创新学分",
  dataIndex: "availableCredit",
  key: "availableCredit",
  width: 100,
  align: "center",
  render: (text, record, index) => {
    return (<Button onClick={() => { this.showAvailableCreditModal(record) }}
      style={{
        marginLeft: '-4px',
        color: '#1890ff',
        fontSize: '8px',
        textDecoration: 'none'
      }}
      disabled={false}
    >{record.availableCredit}</Button>)
  },
},
      
~~~

# 代码器选择器

~~~js
import { SearchBox, TeacheDeptSelector, CodeSelector } from "components";
<Col {...colSpan}>
    <FormItem {...formItemLayout} label="所属单位">
      {getFieldDecorator("department")(<TeacheDeptSelector />)}
    </FormItem>
</Col>
<Col {...colSpan}>
  <FormItem {...formItemLayout} label="课程属性：">
    {getFieldDecorator("courseAttr")(
      <CodeSelector CODE="XTGL_KCSXDMJ" />
    )}
  </FormItem>
</Col>

~~~

# 转圈圈

~~~js
//执行方法前开启圈圈效果
this.setState({
	spinLoading: true
})

//执行方法结束后关闭圈圈效果
this.setState({
	spinLoading: false
})

<Spin spinning={this.state.spinLoading} tip="处理中，请稍等...(若提示处理失败或等待时间过长，请重试)">
 //包裹的内容
</Spin>
~~~

# 将多个对象合并到某个对象

~~~js
const merge = (target, ...sources) => Object.assign(target, ...sources);
~~~

# 合并后返回一个新对象

~~~js
const merge = (...sources) => Object.assign({}, ...sources);
~~~

# 查询最新一条数据

~~~sql
select * FROM (select tt.*, row_number() over(partition by tt.jgh,tt.xnxq order by tt.jgh,tt.xnxq desc) rn FROM T_GZL_TJJG tt
~~~



## 自定义组件使用

## 年级、院系单位、专业

~~~js
import {CodeSelector,DepartSelector, MajorSelector } from "@/components"
<Row>
     <Col {...colSpan}>
         <FormItem {...formItemLayout} label="年级：">
             {getFieldDecorator('grade', {
                 rules: [
                 ]
             })(
                 <CodeSelector CODE="NJ" />
             )}
         </FormItem>
     </Col>
     <Col {...colSpan}>
         <FormItem {...formItemLayout} label="学院:">
             {getFieldDecorator('departId', {
                 rules: [
                 ]
             })(
                 <DepartSelector />
             )}
         </FormItem>
     </Col>
     <Col {...colSpan}>
         <FormItem {...formItemLayout} label="专业:">
             {getFieldDecorator('majorId', {
                 rules: [
                 ]
             })(
                 <MajorSelector />
             )}
         </FormItem>
     </Col>
 
 </Row>
~~~

# 按钮属性

~~~js
<Button
    className="ml10"
    type="primary"
    htmlType="submit"
    onClick={this.onSearch}
>
    查询
</Button>
~~~

# 照片授权

~~~js
import { hex_md5 } from 'utils/MD5'
import { auth,config as globalConfig } from "app/config/global";
function timestamp(url) {
  
  if (url.indexOf("?") > -1) {
    url = url + "&_t=" + getTimestamp
  } else {
    url = url + "?_t=" + getTimestamp
  }
  return url
}

const zximgprops = {
      name: "file",
      action: timestamp(gradeExamManageApi.uploadStudentPictureByOne),
      headers: {
        permission: 'gradeExamPicture:uploadStudentPictureByOne',
        csrfToken:hex_md5(getTimestamp + globalConfig.tokenKey)
      },
      data: (file) => {
        return { postgraduateId: this.props.selectRow.postgraduateId, grade: this.props.selectRow.grade, studentPictureType: "student", file };
      },
      onChange(info) {
        console.log("info", info);
        const file = info.fileList[info.fileList.length - 1];

        _this.setState({ loading: false, zxfileList: [file] });

      }
    };
~~~

# 行点击选中

~~~js
//行点击选中
const onRowClick = (selectedRow, key) => {
    const { selectedRowKeys, selectedRows } = this.state;
    if (selectedRowKeys.includes(key)) {
        this.setState({
            selectedRowKeys: selectedRowKeys.filter((item) => item !== key),
            selectedRows: selectedRows.filter((item) => item !== selectedRow)
        }, () => {
            checkStatueListener();
        });
    } else {
        this.setState({
            selectedRowKeys: [...selectedRowKeys, key],
            selectedRows: [...selectedRows, selectedRow]
        }, () => {
            checkStatueListener();
        });
    }
};

const checkStatueListener = () => {
    console.log('----选中后接着执行动作-----')
};
     
//表单
rowKey: (record) => record.semesterCourseId,
onRow: (record) => ({ onClick: () => onRowClick(record, record.semesterCourseId) }),
~~~

# 页面排序

~~~js
// 1.表单排序修改方法
handleTableChange = (pagination, filters, sorter) => {
    this.params.sorter = sorter;
    this.indexQuery();
};
    
// 2.表单查询
indexQuery = params => {
    let _params = {
        ...this.params,
        ...params
    };
    this.setState({
        loading: true
    });
    // 排序
    if (_params.sorter && _params.sorter.field && _params.sorter.order) {
        _params["attributeNamesForOrderBy"] = {
            [_params.sorter.field]: _params.sorter.order.substring(0, _params.sorter.order.length - 3)
        };
        delete _params.sorter;
    }
    selectQuery(_params, authority.query, (data) => {
       
    });
};
    
//3.columns 中 sorter: true,
{
    title: '学年学期',
    dataIndex: 'semesterId',
    key: 'semesterId',
    align: 'center',
    width: '100px',
    sorter: true,
    // defaultSortOrder: "ascend",
},

//4.表单 onChange
onChange: this.handleTableChange,
 
//5.后台接口 SemesterCourseEntity 实体中含有columns中key的字段
PageHelperUtil.startPage(
        pageQueryParam.getPageNo(),
        pageQueryParam.getPageSize(),
        PageUtil.getOrderBy(
                pageQueryParam.getAttributeNamesForOrderBy(),
                SemesterCourseEntity.class));
//6.SemesterCourseEntity 中对应 @Column("KCLBM")
@Column("KCLBM")
private java.lang.String courseClassifyCode;

@Column("KCLBM")
private java.lang.String courseClassifyName;
~~~

# 移动数组内元素

~~~js
// 移动数组内的元素
const arrayMoveMutate = (array, from, to) => {
    array.splice(to < 0 ? array.length + to : to, 0, array.splice(from, 1)[0]);
};
export const arrayMove = (array, from, to) => {
    array = array.slice();
    arrayMoveMutate(array, from, to);
    return array;
};
~~~

# 参数 FormData

~~~js
const paramData = new FormData();
paramData.append('fileName', fileName)
paramData.append('file', fileList[0]);
paramData.append('importRemark', formData.importRemark);
paramData.append('fileUid', uuid);
paramData.append('exportType', formData.exportType);

//后台接受参数方式 @RequestParam("file")
@ApiOperation("学生照片上传_批量上传")
@RequestMapping(value = "/uploadStudentPicture", method = RequestMethod.POST)
Result<Object> uploadStudentPicture(@RequestParam("file") MultipartFile file, @RequestParam("exportType") String exportType);
~~~

#  Tooltip 提示

~~~js
import { Tooltip } from "antd";
render: (text) => (
    <Tooltip placement="topLeft" title={text}>
        <div style={{ overflow: "hidden", textOverflow: "ellipsis", "whiteSpace": 'nowrap' }}>{text}</div>
    </Tooltip>
)
~~~

# Switch 开关

~~~js
// 是否启用 switch 开关 回调函数
onSwitchChange = (record, text) => {
    console.log('record', record);
    console.log('text', text);
    let param = {
        ...record,
        studentLabelEnableSign: text ? "1" : "0",
    }
    studentLabelUpdate(param, 'studentLabel:update', () => {
        this.onSearch({ pageNo: this.params.pageNo });
    });
}
   
//表单
{
    title: '是否启用',
    dataIndex: 'studentLabelEnableSign',
    key: 'studentLabelEnableSign',
    width: '11%',
    align: 'center',
    render: (text, record) => {
        return (
            <div>
                <Switch
                    // checkedChildren={"是"}
                    // unCheckedChildren={"否"}
                    checkedChildren={<Icon type="check" />}
                    unCheckedChildren={<Icon type="close" />}
                    checked={record.studentLabelEnableSign == "1" ? true : false}
                    onChange={this.onSwitchChange.bind(text, record)}
                />
            </div>
        );
    }
},
    
~~~



# 区间查询框

~~~js
<Col {...colSpan}>
                            <FormItem {...formItemLayout} label="正考:">
                                <Row>
                                    <Col span={11}>
                                        <FormItem>
                                            {getFieldDecorator('scoreMin', {
                                                rules: [
                                                ]
                                            })(
                                                <InputNumber placeholder='>=' style={{ width: '100%' }} />
                                            )}
                                        </FormItem>
                                    </Col>

                                    <Col span={2} className="tc">~</Col>

                                    <Col span={11}>
                                        <FormItem>
                                            {getFieldDecorator('scoreMax', {
                                                rules: [
                                                ]
                                            })(
                                                <InputNumber placeholder='<=' style={{ width: '100%' }} />
                                            )}
                                        </FormItem>
                                    </Col>
                                </Row>
                            </FormItem>
                        </Col>
~~~

# 选择查询

~~~js
<Col {...colSpan}>
                                <FormItem
                                    {...formItemLayout}
                                    label="学年学期："
                                >
                                    {getFieldDecorator('semesterId', {
                                        initialValue: this.state.semesterNow.semesterId,
                                    })(
                                        <Select placeholder="请选择"
                                            optionFilterProp="children"
                                            showSearch
                                            onSelect={(value, semestervalue) => {
                                                this.props.onSearch({
                                                    semesterId: value,
                                                    semester: semestervalue.props.semestervalue || ''
                                                });
                                            }}
                                            onFocus={this.getSemesterList}
                                        >
                                            {this.state.semesterList.map(val =>
                                                <Option key={val.id} semestervalue={val.name}
                                                    value={val.name}>{val.name}</Option>
                                            )}
                                        </Select>
                                    )}
                                </FormItem>
                            </Col>
~~~







 