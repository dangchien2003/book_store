import { useState } from 'react'
import { List, ListItemButton, ListItemText, Collapse, Box } from '@mui/material'
import { ExpandLess, ExpandMore } from '@mui/icons-material'
import { Link } from 'react-router-dom'


export default function SidebarMenu() {
  const [open, setOpen] = useState(null)

  const handleClick = (index) => {
    setOpen(open === index ? null : index)
  }

  return (
    <Box sx={{
      height: {
        lg: '100vh',
        xs: 'auto'
      },
      width: {
        lg: '20%',
        xs: '100vw'
      },
      backgroundColor: {
        xs: '#f0f0f0'
      },
      borderRadius: {
        xs: '0 0 10px 10px'
      }
    }}>
      <List component='nav' >
        {menuData.map((item, index) => (
          <Box key={item.title}>
            <ListItemButton onClick={() => handleClick(index)} sx={{ background: open === index ? '#f1f1f3' : 'transparent' }} >
              <ListItemText primary={item.title} />
              {open === index ? <ExpandLess /> : <ExpandMore />}
            </ListItemButton>
            <Collapse in={open === index} timeout='auto' unmountOnExit sx={open === index ? { backgroundColor: 'black' } : undefined}>
              <List component='div' disablePadding sx={{ background: open === index ? '#f1f1f3' : 'transparent' }}>
                {item.children.map((subItem, index) => (
                  <Link key={index} to={subItem.link} style={{ textDecoration: 'none' }}>
                    <ListItemButton sx={{ pl: 4 }}>
                      <ListItemText primary={subItem.text} sx={{ color: 'black' }} />
                    </ListItemButton>
                  </Link>
                ))}
              </List>
            </Collapse>
          </Box>
        ))
        }
      </List >
    </Box >
  )
}


const menuData = [
  {
    title: 'Quản lý sách',
    children: [
      {
        text: 'Tìm kiếm',
        link: '/manager/book'
      },
      {
        text: 'Thêm mới',
        link: '/manager/book/add'
      },
      {
        text: 'Cập nhật kho',
        link: '/manager/warehouse'
      }]
  },
  {
    title: 'Quản lý tài khoản và quyền',
    children: [
      {
        text: 'Số lượng sách',
        link: '/manager/account'
      },
      {
        text: 'Thêm mới sách',
        link: '/manager/account'
      },
      {
        text: 'Cập nhật kho',
        link: '/manager/account'
      }]
  },
  {
    title: 'Quản lý danh mục',
    children: [
      {
        text: 'Số lượng sách',
        link: '/manager/account'
      },
      {
        text: 'Thêm mới sách',
        link: '/manager/account'
      },
      {
        text: 'Cập nhật kho',
        link: '/manager/account'
      }]
  },
  {
    title: 'Quản lý tác giả',
    children: [
      {
        text: 'Số lượng sách',
        link: '/manager/account'
      },
      {
        text: 'Thêm mới sách',
        link: '/manager/account'
      },
      {
        text: 'Cập nhật kho',
        link: '/manager/account'
      }]
  },
  {
    title: 'Quản lý thống kê',
    children: [
      {
        text: 'Báo cáo hàng tháng',
        link: '/manager/product'
      },
      {
        text: 'Tổng quan theo năm',
        link: '/manager/account'
      },
      {
        text: 'Xu hướng sản phẩm',
        link: '/manager/account'
      }
    ]
  }]

