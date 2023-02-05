import request from '@/utils/request'

// 查询租户套餐列表
export function listTenantPackage(query) {
  return request({
    url: '/system/tenantPackage/list',
    method: 'get',
    params: query
  })
}

// 查询租户套餐详细
export function getTenantPackage(packageId) {
  return request({
    url: '/system/tenantPackage/' + packageId,
    method: 'get'
  })
}

// 新增租户套餐
export function addTenantPackage(data) {
  return request({
    url: '/system/tenantPackage',
    method: 'post',
    data: data
  })
}

// 修改租户套餐
export function updateTenantPackage(data) {
  return request({
    url: '/system/tenantPackage',
    method: 'put',
    data: data
  })
}

// 租户套餐状态修改
export function changePackageStatus(packageId, status) {
  const data = {
    packageId,
    status
  }
  return request({
    url: '/system/tenantPackage/changeStatus',
    method: 'put',
    data: data
  })
}


// 删除租户套餐
export function delTenantPackage(packageId) {
  return request({
    url: '/system/tenantPackage/' + packageId,
    method: 'delete'
  })
}
