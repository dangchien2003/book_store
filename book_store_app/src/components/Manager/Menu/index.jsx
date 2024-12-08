import { useEffect, useState } from 'react'
import { List, ListItemButton, ListItemText, Collapse, Box, useMediaQuery, createTheme } from '@mui/material'
import { ExpandLess, ExpandMore, Menu, MenuOpen } from '@mui/icons-material'
import { Link } from 'react-router-dom'

export default function SidebarMenu() {
  const theme = createTheme()
  const isLg = useMediaQuery(theme.breakpoints.up('lg'))

  const [open, setOpen] = useState(null)
  const [menuOpen, setMenuOpen] = useState(isLg)
  const [minHeight, setMinHeight] = useState(0)

  const handleClick = (index) => {
    setOpen(open === index ? null : index)
  }

  useEffect(() => {
    const updateHeight = () => {
      setMinHeight(Math.max(
        document.documentElement.scrollHeight
      ))
    }

    updateHeight()

    window.addEventListener('resize', updateHeight)

    return () => {
      window.removeEventListener('resize', updateHeight)
    }
  }, [])

  return (
    <Box sx={{
      position: {
        lg: 'static',
        xs: 'sticky'
      },
      top: 0,
      zIndex: 9000,
      width: {
        lg: '20%',
        xs: '70vw'
      }
    }}>
      {!menuOpen && <Menu
        sx={{
          fontSize: '35px',
          position: 'absolute',
          top: 0,
          zIndex: 9999,
          left: '5px',
          display: {
            lg: 'none',
            xs: 'block'
          }
        }}
        onClick={() => setMenuOpen(true)} />}
      <Box sx={{
        display: !menuOpen && !isLg && 'none',
        minHeight: minHeight + 'px',
        position: {
          xs: 'relative'
        },
        backgroundColor: {
          xs: '#f0f0f0'
        },
        borderRadius: {
          xs: '0 0 10px 10px'
        },
        animation: 'showMenu 0.2s ease-out',
        '@keyframes showMenu': {
          '0%': {
            width: '0%'
          },
          '100%': {
            width: '100%'
          }
        }
      }}>
        <Box sx={{
          height: {
            lg: 0,
            xs: '30px'
          }
        }}>
          {menuOpen && <MenuOpen
            sx={{
              fontSize: '35px',
              position: 'absolute',
              top: 0,
              right: 0,
              display: {
                lg: 'none',
                xs: 'block'
              }
            }}
            onClick={() => setMenuOpen(false)} />
          }
        </Box>
        <List component='nav' >
          {menuData.map((item, index) => (
            <Box key={item.title}>
              <ListItemButton onClick={() => handleClick(index)} sx={{ background: open === index ? '#f1f1f3' : 'transparent' }} >
                <ListItemText primary={item.title} />
                {open === index ? <ExpandLess /> : <ExpandMore />}
              </ListItemButton>
              <Collapse in={open === index} timeout='auto' unmountOnExit sx={open === index ? { backgroundColor: 'black' } : undefined}>
                <List component='div' disablePadding sx={{ background: open === index ? '#f6f6f7' : 'transparent' }}>
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

