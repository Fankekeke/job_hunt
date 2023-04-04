<template>
  <a-card :bordered="false" class="card-area">
    <div :class="advanced ? 'search' : null">
      <!-- 搜索区域 -->
      <a-form layout="horizontal">
        <a-row :gutter="15">
          <div :class="advanced ? null: 'fold'">
            <a-col :md="6" :sm="24">
              <a-form-item
                label="企业名称"
                :labelCol="{span: 4}"
                :wrapperCol="{span: 18, offset: 2}">
                <a-input v-model="queryParams.enterpriseName"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item
                label="工作地点"
                :labelCol="{span: 4}"
                :wrapperCol="{span: 18, offset: 2}">
                <a-input v-model="queryParams.address"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item
                label="岗位名称"
                :labelCol="{span: 4}"
                :wrapperCol="{span: 18, offset: 2}">
                <a-input v-model="queryParams.postName"/>
              </a-form-item>
            </a-col>
          </div>
          <span style="float: right; margin-top: 3px;">
            <a-button type="primary" @click="search">查询</a-button>
            <a-button style="margin-left: 8px" @click="reset">重置</a-button>
          </span>
        </a-row>
      </a-form>
    </div>
    <div>
      <div class="operator">
<!--        <a-button type="primary" ghost @click="add">新增</a-button>-->
        <a-button @click="batchDelete">删除</a-button>
      </div>
      <!-- 表格区域 -->
      <a-table ref="TableInfo"
               :columns="columns"
               :rowKey="record => record.id"
               :dataSource="dataSource"
               :pagination="pagination"
               :loading="loading"
               :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
               :scroll="{ x: 900 }"
               @change="handleTableChange">
        <template slot="titleShow" slot-scope="text, record">
          <template>
            <a-tooltip>
              <template slot="title">
                {{ record.title }}
              </template>
              {{ record.title.slice(0, 8) }} ...
            </a-tooltip>
          </template>
        </template>
        <template slot="contentShow" slot-scope="text, record">
          <template>
            <a-tooltip>
              <template slot="title">
                {{ record.content }}
              </template>
              {{ record.content.slice(0, 30) }} ...
            </a-tooltip>
          </template>
        </template>
        <template slot="operation" slot-scope="text, record">
          <a-icon v-if="record.delFlag === 1" type="caret-down" @click="audit(record.id, 0)" title="下 架" style="margin-right: 10px"></a-icon>
          <a-icon v-if="record.delFlag === 0" type="caret-up" @click="audit(record.id, 1)" title="上 架" style="margin-right: 10px"></a-icon>
          <a-icon type="cloud" @click="handlePostViewOpen(record)" title="详 情"></a-icon>
          <a-icon type="setting" theme="twoTone" twoToneColor="#4a9ff5" @click="edit(record)" title="修 改" style="margin-left: 15px"></a-icon>
        </template>
      </a-table>
    </div>
    <post-add
      v-if="pluralismAdd.visiable"
      @close="handlepluralismAddClose"
      @success="handlepluralismAddSuccess"
      :pluralismAddVisiable="pluralismAdd.visiable">
    </post-add>
    <post-edit
      ref="pluralismEdit"
      @close="handlepluralismEditClose"
      @success="handlepluralismEditSuccess"
      :pluralismEditVisiable="pluralismEdit.visiable">
    </post-edit>
    <post-view
      @close="handlePostViewClose"
      :pluralismShow="postView.visiable"
      :pluralismData="postView.data">
    </post-view>
  </a-card>
</template>

<script>
import RangeDate from '@/components/datetime/RangeDate'
import postAdd from './PostAdd'
import postEdit from './PostEdit'
import {mapState} from 'vuex'
import moment from 'moment'
import PostView from './PostView.vue'
moment.locale('zh-cn')

export default {
  name: 'pluralism',
  components: {PostView, postAdd, postEdit, RangeDate},
  data () {
    return {
      postView: {
        visiable: false,
        data: null
      },
      advanced: false,
      pluralismAdd: {
        visiable: false
      },
      pluralismEdit: {
        visiable: false
      },
      queryParams: {},
      filteredInfo: null,
      sortedInfo: null,
      paginationInfo: null,
      dataSource: [],
      selectedRowKeys: [],
      loading: false,
      pagination: {
        pageSizeOptions: ['10', '20', '30', '40', '100'],
        defaultCurrent: 1,
        defaultPageSize: 10,
        showQuickJumper: true,
        showSizeChanger: true,
        showTotal: (total, range) => `显示 ${range[0]} ~ ${range[1]} 条记录，共 ${total} 条记录`
      },
      userList: []
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    columns () {
      return [{
        title: '岗位编号',
        dataIndex: 'code'
      }, {
        title: '岗位名称',
        dataIndex: 'postName'
      }, {
        title: '工作时间',
        dataIndex: 'workTime',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '工作时段',
        dataIndex: 'workHour',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '薪资',
        dataIndex: 'salary',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '工作地点',
        dataIndex: 'address',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '所属行业',
        dataIndex: 'industryName',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '企业名称',
        dataIndex: 'enterpriseName',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '企业照片',
        dataIndex: 'images',
        customRender: (text, record, index) => {
          if (!record.images) return <a-avatar shape="square" icon="user" />
          return <a-popover>
            <template slot="content">
              <a-avatar shape="square" size={132} icon="user" src={ 'http://127.0.0.1:9527/imagesWeb/' + record.images } />
            </template>
            <a-avatar shape="square" icon="user" src={ 'http://127.0.0.1:9527/imagesWeb/' + record.images } />
          </a-popover>
        }
      }, {
        title: '发布时间',
        dataIndex: 'createDate',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '操作',
        dataIndex: 'operation',
        scopedSlots: {customRender: 'operation'}
      }]
    }
  },
  mounted () {
    this.fetch()
  },
  methods: {
    audit (id, status) {
      this.$get('/cos/post-info/audit', {postId: id, status}).then((r) => {
        this.$message.success('修改成功')
        this.search()
      })
    },
    handlePostViewClose () {
      this.postView.visiable = false
    },
    handlePostViewOpen (row) {
      this.postView.data = row
      this.postView.visiable = true
    },
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
    },
    add () {
      this.pluralismAdd.visiable = true
    },
    handlepluralismAddClose () {
      this.pluralismAdd.visiable = false
    },
    handlepluralismAddSuccess () {
      this.pluralismAdd.visiable = false
      this.$message.success('新增岗位成功')
      this.search()
    },
    edit (record) {
      this.$refs.pluralismEdit.setFormValues(record)
      this.pluralismEdit.visiable = true
    },
    handlepluralismEditClose () {
      this.pluralismEdit.visiable = false
    },
    handlepluralismEditSuccess () {
      this.pluralismEdit.visiable = false
      this.$message.success('修改岗位成功')
      this.search()
    },
    handleDeptChange (value) {
      this.queryParams.deptId = value || ''
    },
    batchDelete () {
      if (!this.selectedRowKeys.length) {
        this.$message.warning('请选择需要删除的记录')
        return
      }
      let that = this
      this.$confirm({
        title: '确定删除所选中的记录?',
        content: '当您点击确定按钮后，这些记录将会被彻底删除',
        centered: true,
        onOk () {
          let ids = that.selectedRowKeys.join(',')
          that.$delete('/cos/post-info/' + ids).then(() => {
            that.$message.success('删除成功')
            that.selectedRowKeys = []
            that.search()
          })
        },
        onCancel () {
          that.selectedRowKeys = []
        }
      })
    },
    search () {
      let {sortedInfo, filteredInfo} = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      this.fetch({
        sortField: sortField,
        sortOrder: sortOrder,
        ...this.queryParams,
        ...filteredInfo
      })
    },
    reset () {
      // 取消选中
      this.selectedRowKeys = []
      // 重置分页
      this.$refs.TableInfo.pagination.current = this.pagination.defaultCurrent
      if (this.paginationInfo) {
        this.paginationInfo.current = this.pagination.defaultCurrent
        this.paginationInfo.pageSize = this.pagination.defaultPageSize
      }
      // 重置列过滤器规则
      this.filteredInfo = null
      // 重置列排序规则
      this.sortedInfo = null
      // 重置查询参数
      this.queryParams = {}
      this.fetch()
    },
    handleTableChange (pagination, filters, sorter) {
      // 将这三个参数赋值给Vue data，用于后续使用
      this.paginationInfo = pagination
      this.filteredInfo = filters
      this.sortedInfo = sorter

      this.fetch({
        sortField: sorter.field,
        sortOrder: sorter.order,
        ...this.queryParams,
        ...filters
      })
    },
    fetch (params = {}) {
      // 显示loading
      this.loading = true
      if (this.paginationInfo) {
        // 如果分页信息不为空，则设置表格当前第几页，每页条数，并设置查询分页参数
        this.$refs.TableInfo.pagination.current = this.paginationInfo.current
        this.$refs.TableInfo.pagination.pageSize = this.paginationInfo.pageSize
        params.size = this.paginationInfo.pageSize
        params.current = this.paginationInfo.current
      } else {
        // 如果分页信息为空，则设置为默认值
        params.size = this.pagination.defaultPageSize
        params.current = this.pagination.defaultCurrent
      }
      params.enterpriseId = this.currentUser.userId
      this.$get('/cos/post-info/page', {
        ...params
      }).then((r) => {
        let data = r.data.data
        const pagination = {...this.pagination}
        pagination.total = data.total
        this.dataSource = data.records
        this.pagination = pagination
        // 数据加载完毕，关闭loading
        this.loading = false
      })
    }
  },
  watch: {}
}
</script>
<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
