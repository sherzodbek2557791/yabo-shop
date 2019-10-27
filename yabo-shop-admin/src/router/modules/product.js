/** When your routing table is too long, you can split it into small modules **/

import Layout from '@/layout'

const productRouter = {
  path: '/product',
  component: Layout,
  redirect: '/product/list',
  name: 'Products',
  meta: {
    title: 'Products',
    icon: 'table'
  },
  children: [
    {
      path: 'list',
      component: () => import('@/views/table/complex-table'),
      name: 'Products',
      meta: { title: 'Product list' }
    },
    {
      path: 'categories',
      component: () => import('@/views/table/drag-table'),
      name: 'Categories',
      meta: { title: 'Product categories' }
    }
  ]
};
export default productRouter
