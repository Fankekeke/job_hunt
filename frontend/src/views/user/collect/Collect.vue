<template>
  <div style="background:#ECECEC; padding:30px;margin-top: 30px;width: 100%">
    <a-row :gutter="55">
      <a-col v-if="collectList.length === 0" :span="24">
        <div style="color: rgba(0,0,0,.85);font-size: 24px;line-height: 1.8;text-align: center;height: 250px;">
          <p style="margin-top: 100px">暂无收藏记录</p>
        </div>
      </a-col>
      <a-col :span="8" v-for="(item, index) in collectList" :key="index">
        <a-card hoverable :bordered="false">
          <a-card-meta :title="item.title" :description="item.content.slice(0, 20) + '...'"></a-card-meta>
          <template slot="actions" class="ant-card-actions">
            <a-icon key="delete" type="delete" @click="collectPostCheck(1, item.postId)"/>
          </template>
          <div style="margin-top: 15px">
            <a-row :gutter="15">
              <a-col :span="12"><b>访问量：</b>
                {{ item.pageviews }}
              </a-col>
              <a-col :span="12">
                <a-tag v-if="item.deleteFlag == 1" color="#f50">已删除</a-tag>
              </a-col>
              <a-col :span="24" style="margin-top: 10px"><b>收藏时间：</b>
                {{ item.createDate }}
              </a-col>
            </a-row>
          </div>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<script>
import {mapState} from 'vuex'

export default {
  name: 'Collect',
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    })
  },
  data () {
    return {
      loading: false,
      collectList: []
    }
  },
  mounted () {
    this.getCollectList()
  },
  methods: {
    collectPostCheck (deleteFlag, postId) {
      this.$post(`/cos/collect-info`, {userId: this.currentUser.userId, postId, deleteFlag}).then((r) => {
        this.getCollectList()
        this.$message.success(deleteFlag === 0 ? '收藏贴子成功！' : '取消收藏成功！')
      })
    },
    getCollectList () {
      this.$get(`/cos/collect-info/list/${this.currentUser.userId}`).then((r) => {
        this.collectList = r.data.data
      })
    }
  }
}
</script>

<style scoped>

</style>
