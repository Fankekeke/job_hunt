<template>
  <a-modal v-model="show" title="新增兼职" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="back" @click="onClose">
        取消
      </a-button>
      <a-button key="submit" type="primary" :loading="loading" @click="handleSubmit">
        提交
      </a-button>
    </template>
    <a-form :form="form" layout="vertical">
      <a-row :gutter="20">
        <a-col :span="8">
          <a-form-item label='兼职标题' v-bind="formItemLayout">
            <a-input v-decorator="[
            'title',
            { rules: [{ required: true, message: '请输入名称!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label='岗位名称' v-bind="formItemLayout">
            <a-input v-decorator="[
            'postName',
            { rules: [{ required: true, message: '请输入岗位名称!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label='结算方式' v-bind="formItemLayout">
            <a-input v-decorator="[
            'paymentMethod',
            { rules: [{ required: true, message: '请输入结算方式!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='岗位描述' v-bind="formItemLayout">
            <a-textarea :rows="6" v-decorator="[
            'describe',
             { rules: [{ required: true, message: '请输入岗位描述!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label='学历要求' v-bind="formItemLayout">
            <a-input v-decorator="[
            'academic',
            { rules: [{ required: true, message: '请输入学历要求!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label='工作时间' v-bind="formItemLayout">
            <a-input v-decorator="[
            'workTime',
            { rules: [{ required: true, message: '请输入工作时间!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label='工作时段' v-bind="formItemLayout">
            <a-input v-decorator="[
            'workHour',
            { rules: [{ required: true, message: '请输入工作时段!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label='工作地点' v-bind="formItemLayout">
            <a-input v-decorator="[
            'workAddress',
            { rules: [{ required: true, message: '请输入工作地点!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label='工作要求' v-bind="formItemLayout">
            <a-textarea :rows="6" v-decorator="[
            'workRequire',
             { rules: [{ required: true, message: '请输入工作要求!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label='所属行业' v-bind="formItemLayout">
            <a-textarea :rows="6" v-decorator="[
            'industryId',
             { rules: [{ required: true, message: '请输入所属行业!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label='薪资范围' v-bind="formItemLayout">
            <a-textarea :rows="6" v-decorator="[
            'salary',
             { rules: [{ required: true, message: '请输入薪资范围!' }] }
            ]"/>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
import {mapState} from 'vuex'
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
const formItemLayout = {
  labelCol: { span: 24 },
  wrapperCol: { span: 24 }
}
export default {
  name: 'pluralismAdd',
  props: {
    pluralismAddVisiable: {
      default: false
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.pluralismAddVisiable
      },
      set: function () {
      }
    }
  },
  data () {
    return {
      formItemLayout,
      form: this.$form.createForm(this),
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: ''
    }
  },
  methods: {
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
    reset () {
      this.loading = false
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    handleSubmit () {
      // 获取图片List
      let images = []
      this.fileList.forEach(image => {
        images.push(image.response)
      })
      this.form.validateFields((err, values) => {
        values.images = images.length > 0 ? images.join(',') : null
        if (!err) {
          this.loading = true
          this.$post('/cos/pluralism-info', {
            ...values
          }).then((r) => {
            this.reset()
            this.$emit('success')
          }).catch(() => {
            this.loading = false
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
