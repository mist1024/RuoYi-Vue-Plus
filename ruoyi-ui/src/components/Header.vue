<template>
  <div class="header">
    标题
    <el-row style="height: 81px;width: 100%">
      <el-col :xl="3" :lg="3" :md="24" :sm="24" :xs="24" class="header-logo hidden-lg-and-down" >
        <el-image
          :src="logoUrl"
          fit="fill"
          style="width: 248px; height: 60px"
        />
      </el-col>
      <el-col :xl="11" :lg="11" :md="24" :sm="24" :xs="24" class="hidden-md-and-down">
        <p class="header-title">{{ title }}</p>
      </el-col>
      <el-col :xl="10" :lg="10" :md="24" :sm="24" :xs="24">
        <el-row type="flex" justify="end" align="middle" style="height: 81px;">
          <el-col :xl="2" :lg="2" :md="8" :sm="8" :xs="8" style="text-align: right;padding-right: 10px">
            <el-avatar :size="30" :src="avatar" />
          </el-col>
          <el-col :xl="3" :lg="4" :md="8" :sm="8" :xs="8">
            <div style="font-size: 16px;color: #FFFFFF;">{{ name }}</div>
          </el-col>
          <el-col :xl="4" :lg="6" :md="8" :sm="8" :xs="8">
            <el-divider direction="vertical" />
            <el-button style="background: transparent;border: none" type="primary" circle @click.native="goHome"><svg-icon style="font-size: 22px" icon-class="home" /></el-button>
            <el-button style="background: transparent;border: none" type="primary" circle @click.native="logout"><svg-icon style="font-size: 20px" icon-class="logout" /></el-button>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { title } from '@/settings'
import logoPng from '@/assets/logo/logo.png'

export default {
  name: 'Header',
  data() {
    return {
      logoUrl: logoPng,
      title: title
    }
  },
  computed: {
    ...mapGetters([
      'avatar',
      'name'
    ])
  },
  methods: {
    goHome() {
      this.$router.push({ path: '/' })
    },
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },
    logout() {
      this.$confirm('即将退出系统, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteUserInfo()
      }).catch(() => {

      })
    },
    async deleteUserInfo() {
      await this.$store.dispatch('user/logout')
      await this.$store.dispatch('permission/clearRoutes')
      this.$router.push(`/login?redirect=${this.$route.fullPath}`)
    }
  },
  mounted() {
    console.log("123")
  }
}
</script>

<style lang="scss" scoped>
  p {
    margin: 2px;
  }

  .header {

    z-index: 10000;
    color: #d9d9d9;
    background-image: linear-gradient(to left, #db7878 0%, #fc4444 100%);
    background-size: 100%;
    height: 80px;
    width: 100%;
  }

  .header-logo {
    text-align: left;
    vertical-align: middle;
    height: 81px;
    padding-top: 10px;
    padding-left: 10px;
  }

  .header-title {
    text-align: left;
    color: #ffffff;
    font-size: 36px;
    line-height: 80px;
    font-weight: 700;
    letter-spacing: 4px;
    padding-left: 10px;
    //text-shadow:2px 3px 4px #403e3e;
  }

  .header-block {
    min-height: 80px;
  }

</style>
