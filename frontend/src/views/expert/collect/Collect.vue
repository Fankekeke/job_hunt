<template>
  <a-card :bordered="false" class="card-area">
    <div :class="advanced ? 'search' : null">
      <!-- 搜索区域 -->
      <a-form layout="horizontal">
        <a-row :gutter="15">
          <div :class="advanced ? null: 'fold'">
            <a-col :md="6" :sm="24">
              <a-form-item
                label="求职者名称"
                :labelCol="{span: 4}"
                :wrapperCol="{span: 18, offset: 2}">
                <a-input v-model="queryParams.expertName"/>
              </a-form-item>
            </a-col>
             <a-col :md="6" :sm="24">
              <a-form-item
                label="企业名称"
                :labelCol="{span: 4}"
                :wrapperCol="{span: 18, offset: 2}">
                <a-input v-model="queryParams.enterpriseName"/>
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
      <a-tabs default-active-key="1" @change="callback">
        <a-tab-pane key="2" tab="收藏兼职">
          <!-- 表格区域 -->
          <a-table ref="TableInfo"
                   :columns="pluralismColumns"
                   :rowKey="record => record.id"
                   :dataSource="dataSource"
                   :pagination="pagination"
                   :loading="loading"
                   :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
                   :scroll="{ x: 900 }"
                   @change="handleTableChange">
          </a-table>
        </a-tab-pane>
        <a-tab-pane key="3" tab="收藏岗位">
          <!-- 表格区域 -->
          <a-table ref="TableInfo"
                   :columns="postColumns"
                   :rowKey="record => record.id"
                   :dataSource="dataSource"
                   :pagination="pagination"
                   :loading="loading"
                   :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
                   :scroll="{ x: 900 }"
                   @change="handleTableChange">
          </a-table>
        </a-tab-pane>
      </a-tabs>
    </div>
  </a-card>
</template>

<script>
import RangeDate from '@/components/datetime/RangeDate'
import {mapState} from 'vuex'
import moment from 'moment'
moment.locale('zh-cn')

export default {
  name: 'User',
  components: {RangeDate},
  data () {
    return {
      userView: {
        visiable: false,
        data: null
      },
      advanced: false,
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
      userList: [],
      currentKey: 1
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    columns () {
      return [{
        title: '求职者编号',
        dataIndex: 'expertCode'
      }, {
        title: '企业编号',
        dataIndex: 'enterCode'
      }, {
        title: '求职者名称',
        dataIndex: 'expertName'
      }, {
        title: '企业名称',
        dataIndex: 'enterName'
      }, {
        title: '求职者头像',
        dataIndex: 'expertImages',
        customRender: (text, record, index) => {
          if (!record.expertImages) return <a-avatar shape="square" icon="user" />
          return <a-popover>
            <template slot="content">
              <a-avatar shape="square" size={132} icon="user" src={ 'http://127.0.0.1:9527/imagesWeb/' + record.expertImages } />
            </template>
            <a-avatar shape="square" icon="user" src={ 'http://127.0.0.1:9527/imagesWeb/' + record.expertImages } />
          </a-popover>
        }
      }, {
        title: '收藏时间',
        dataIndex: 'createDate'
      }]
    },
    postColumns () {
      return [{
        title: '求职者名称',
        dataIndex: 'expertName'
      }, {
        title: '企业名称',
        dataIndex: 'enterName'
      }, {
        title: '求职者头像',
        dataIndex: 'expertImages',
        customRender: (text, record, index) => {
          if (!record.expertImages) return <a-avatar shape="square" icon="user" />
          return <a-popover>
            <template slot="content">
              <a-avatar shape="square" size={132} icon="user" src={ 'http://127.0.0.1:9527/imagesWeb/' + record.expertImages } />
            </template>
            <a-avatar shape="square" icon="user" src={ 'http://127.0.0.1:9527/imagesWeb/' + record.expertImages } />
          </a-popover>
        }
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
        title: '收藏时间',
        dataIndex: 'createDate'
      }]
    },
    pluralismColumns () {
      return [{
        title: '求职者名称',
        dataIndex: 'expertName'
      }, {
        title: '企业名称',
        dataIndex: 'enterName'
      }, {
        title: '求职者头像',
        dataIndex: 'expertImages',
        customRender: (text, record, index) => {
          if (!record.expertImages) return <a-avatar shape="square" icon="user" />
          return <a-popover>
            <template slot="content">
              <a-avatar shape="square" size={132} icon="user" src={ 'http://127.0.0.1:9527/imagesWeb/' + record.expertImages } />
            </template>
            <a-avatar shape="square" icon="user" src={ 'http://127.0.0.1:9527/imagesWeb/' + record.expertImages } />
          </a-popover>
        }
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
        dataIndex: 'workAddress',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '结算方式',
        dataIndex: 'paymentMethod',
        customRender: (text, row, index) => {
          switch (text) {
            case 1:
              return <a-tag>日结</a-tag>
            case 2:
              return <a-tag>周结</a-tag>
            case 3:
              return <a-tag>月结</a-tag>
            case 4:
              return <a-tag>季结</a-tag>
            default:
              return '- -'
          }
        }
      }, {
        title: '收藏时间',
        dataIndex: 'createDate'
      }]
    }
  },
  mounted () {
    this.fetch({type: 2})
  },
  methods: {
    view (row) {
      this.userView.data = row
      this.userView.visiable = true
    },
    handleUserViewClose () {
      this.userView.visiable = false
    },
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
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
          that.$delete('/cos/collect-info/' + ids).then(() => {
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
        type: this.currentKey,
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
    callback (key) {
      this.currentKey = key
      this.fetch({type: key})
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
      if (params.readStatus === undefined) {
        delete params.readStatus
      }
      params.expertCode = this.currentUser.userId
      this.$get('/cos/collect-info/page', {
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
