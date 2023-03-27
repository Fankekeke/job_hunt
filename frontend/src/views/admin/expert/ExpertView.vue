<template>
  <a-modal v-model="show" title="求职者详情" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="expertData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">求职者信息</span></a-col>
        <a-col :span="8"><b>求职者姓名：</b>
          {{ expertData.name }}
        </a-col>
        <a-col :span="8"><b>民族：</b>
          {{ expertData.nationality }}
        </a-col>
        <a-col :span="8"><b>性别：</b>
          {{ expertData.sex }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>政治面貌：</b>
          {{ expertData.politicalStatus }}
        </a-col>
        <a-col :span="8"><b>籍贯：</b>
          {{ expertData.nativePlace }}
        </a-col>
        <a-col :span="8"><b>工作单位：</b>
          {{ expertData.employer }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>职务：</b>
          {{ expertData.position }}
        </a-col>
        <a-col :span="16"><b>通讯地址：</b>
          {{ expertData.address }}
        </a-col>
        <br/>
        <br/>
        <a-col :span="8"><b>手机号：</b>
          {{ expertData.phone }}
        </a-col>
        <a-col :span="8"><b>专业方向一级：</b>
          {{ expertData.levelOne }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <br/>
        <a-col :span="8"><b>出生日期：</b>
          {{ expertData.birthDate }}
        </a-col>
        <a-col :span="16"><b>专业方向二级：</b>
          {{ expertData.levelTwo }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>职称：</b>
          {{ expertData.jobTitle }}
        </a-col>
        <a-col :span="8"><b>特殊称谓：</b>
          {{ expertData.specialAppellation }}
        </a-col>
        <a-col :span="8"><b>邮箱：</b>
          {{ expertData.mail }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>固定电话：</b>
          {{ expertData.fixedTelephone }}
        </a-col>
        <a-col :span="8"><b>创建时间：</b>
          {{ expertData.createDate }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="24"><b>个人简介：</b>
          {{ expertData.profile }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">求职者照片</span></a-col>
        <a-col :span="24">
          <a-upload
            name="avatar"
            action="http://127.0.0.1:9527/file/fileUpload/"
            list-type="picture-card"
            :file-list="fileList"
            @preview="handlePreview"
            @change="picHandleChange"
          >
          </a-upload>
          <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
            <img alt="example" style="width: 100%" :src="previewImage" />
          </a-modal>
        </a-col>
      </a-row>
      <br/>
      <br/>
    </div>
  </a-modal>
</template>

<script>
import moment from 'moment'
moment.locale('zh-cn')
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
export default {
  name: 'expertView',
  props: {
    expertShow: {
      type: Boolean,
      default: false
    },
    expertData: {
      type: Object
    }
  },
  computed: {
    show: {
      get: function () {
        return this.expertShow
      },
      set: function () {
      }
    }
  },
  data () {
    return {
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: ''
    }
  },
  watch: {
    expertShow: function (value) {
      if (value) {
        if (this.expertData.images !== null && this.expertData.images !== '') {
          this.imagesInit(this.expertData.images)
        }
      }
    }
  },
  methods: {
    imagesInit (images) {
      if (images !== null && images !== '') {
        let imageList = []
        images.split(',').forEach((image, index) => {
          imageList.push({uid: index, name: image, status: 'done', url: 'http://127.0.0.1:9527/imagesWeb/' + image})
        })
        this.fileList = imageList
      }
    },
    handleCancel () {
      this.previewVisible = false
    },
    async handlePreview (file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj)
      }
      this.previewImage = file.url || file.preview
      this.previewVisible = true
    },
    picHandleChange ({ fileList }) {
      this.fileList = fileList
    },
    onClose () {
      this.$emit('close')
    }
  }
}
</script>

<style scoped>

</style>
