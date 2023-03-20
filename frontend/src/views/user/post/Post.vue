<template>
  <a-card :bordered="false" class="card-area">
    <div :class="advanced ? 'search' : null">
      <!-- 搜索区域 -->
      <a-form layout="horizontal">
        <a-row :gutter="15">
          <div :class="advanced ? null: 'fold'">
            <a-col :md="6" :sm="24">
              <a-form-item
                label="贴子标题"
                :labelCol="{span: 4}"
                :wrapperCol="{span: 18, offset: 2}">
                <a-input v-model="queryParams.title"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item
                label="发贴人"
                :labelCol="{span: 4}"
                :wrapperCol="{span: 18, offset: 2}">
                <a-input v-model="queryParams.username"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item
                label="所属模块"
                :labelCol="{span: 4}"
                :wrapperCol="{span: 18, offset: 2}">
                <a-select v-model="queryParams.tagId" allowClear>
                  <a-select-option v-for="(item, index) in tagList" :key="index" :value="item.id">{{ item.name }}</a-select-option>
                </a-select>
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
        <a-button type="primary" ghost @click="add">新增</a-button>
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
            <a-badge v-if="record.deleteFlag == 1" status="error"/>
            <a-badge v-if="record.deleteFlag == 0" status="processing"/>
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
          <a-icon v-if="record.deleteFlag == 1" type="caret-up" @click="auditDelete(record)" title="up" style="margin-right: 10px"></a-icon>
          <a-icon type="setting" theme="twoTone" twoToneColor="#4a9ff5" @click="edit(record)" title="修 改"></a-icon>
        </template>
      </a-table>
    </div>
    <post-add
      v-if="postAdd.visiable"
      @close="handlepostAddClose"
      @success="handlepostAddSuccess"
      :postAddVisiable="postAdd.visiable"
      :tagList="tagListData">
    </post-add>
    <post-edit
      ref="postEdit"
      @close="handlepostEditClose"
      @success="handlepostEditSuccess"
      :postEditVisiable="postEdit.visiable"
      :tagList="tagListData">
    </post-edit>
  </a-card>
</template>

<script>
import RangeDate from '@/components/datetime/RangeDate'
import PostAdd from './PostAdd'
import PostEdit from './PostEdit'
import {mapState} from 'vuex'
import moment from 'moment'
moment.locale('zh-cn')

export default {
  name: 'post',
  components: {PostAdd, PostEdit, RangeDate},
  data () {
    return {
      advanced: false,
      postAdd: {
        visiable: false
      },
      postEdit: {
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
      tagList: [],
      tagListData: []
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    columns () {
      return [{
        title: '发贴人',
        dataIndex: 'username',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '头像',
        dataIndex: 'avatar',
        customRender: (text, record, index) => {
          if (!record.avatar) return <a-avatar shape="square" icon="user" />
          return <a-popover>
            <template slot="content">
              <a-avatar shape="square" size={132} icon="user" src={ 'static/avatar/' + record.avatar } />
            </template>
            <a-avatar shape="square" icon="user" src={ 'static/avatar/' + record.avatar } />
          </a-popover>
        }
      }, {
        title: '标题',
        dataIndex: 'title',
        scopedSlots: { customRender: 'titleShow' }
      }, {
        title: '贴子内容',
        dataIndex: 'content',
        scopedSlots: { customRender: 'contentShow' }
      }, {
        title: '访问量',
        dataIndex: 'pageviews',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text + '次'
          } else {
            return '- -'
          }
        }
      }, {
        title: '收藏量',
        dataIndex: 'collect',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text + '收藏'
          } else {
            return '- -'
          }
        }
      }, {
        title: '回复量',
        dataIndex: 'reply',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text + '回复'
          } else {
            return '- -'
          }
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
    this.getTagList()
  },
  methods: {
    auditDelete (row) {
      row.deleteFlag = 0
      this.$put('/cos/post-info', row).then((r) => {
        this.$message.success('恢复贴子成功！')
        this.search()
      })
    },
    getTagList () {
      this.$get('/cos/tag-info/list').then((r) => {
        this.tagList = r.data.data
        let tagListData = []
        r.data.data.forEach(item => {
          tagListData.push({label: item.name, value: item.id})
        })
        this.tagListData = tagListData
      })
    },
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
    },
    add () {
      this.postAdd.visiable = true
    },
    handlepostAddClose () {
      this.postAdd.visiable = false
    },
    handlepostAddSuccess () {
      this.postAdd.visiable = false
      this.$message.success('新增贴子成功')
      this.search()
    },
    edit (record) {
      this.$refs.postEdit.setFormValues(record)
      this.postEdit.visiable = true
    },
    handlepostEditClose () {
      this.postEdit.visiable = false
    },
    handlepostEditSuccess () {
      this.postEdit.visiable = false
      this.$message.success('修改贴子成功')
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
      if (params.tagId === undefined) {
        delete params.tagId
      }
      params.userId = this.currentUser.userId
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
