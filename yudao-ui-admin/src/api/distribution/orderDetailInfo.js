import request from '@/utils/request'

// 创建配货订单明细
export function createOrderDetailInfo(data) {
  return request({
    url: '/distribution/order-detail-info/create',
    method: 'post',
    data: data
  })
}

// 更新配货订单明细
export function updateOrderDetailInfo(data) {
  return request({
    url: '/distribution/order-detail-info/update',
    method: 'put',
    data: data
  })
}

// 删除配货订单明细
export function deleteOrderDetailInfo(id) {
  return request({
    url: '/distribution/order-detail-info/delete?id=' + id,
    method: 'delete'
  })
}

// 获得配货订单明细
export function getOrderDetailInfo(id) {
  return request({
    url: '/distribution/order-detail-info/get?id=' + id,
    method: 'get'
  })
}

// 获得配货订单明细分页
export function getOrderDetailInfoPage(query) {
  return request({
    url: '/distribution/order-detail-info/page',
    method: 'get',
    params: query
  })
}

// 导出配货订单明细 Excel
export function exportOrderDetailInfoExcel(query) {
  return request({
    url: '/distribution/order-detail-info/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}

// 导出配货订单明细标签 Excel
export function exportOrderDetailInfoLableExcel(query) {
  return request({
    url: '/distribution/order-detail-info/export-lable-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}


// 面向上下游导出配货订单明细 Excel
export function exportOrderDetailInfoFacingObjectExcel(query) {
  return request({
    url: '/distribution/order-detail-info/export-facing-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
