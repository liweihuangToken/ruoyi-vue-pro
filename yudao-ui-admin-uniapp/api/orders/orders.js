import upload from '@/utils/upload'
import request from '@/utils/request'

// 通过订单编码或货物编码查询订单详情
export function getOrderDetailInfoByCode(code) {
  return request({
    url: '/distribution/order-detail-info/getByCode?code=' + code,
    method: 'GET'
  })
}

// 更新配货订单明细状态
export function updateOrderStatus(data) {
  return request({
    url: '/distribution/order-detail-info/updateOrderStatus',
    method: 'put',
    data: data
  })
}