import React, { useState, useMemo } from 'react';
import { createEditor } from 'slate';
import { Slate, Editable, withReact } from 'slate-react';
import { withHistory } from 'slate-history';
import { Button, Box, Container, Typography } from '@mui/material';
import { Editor, Transforms } from 'slate';

const RichTextEditor = () => {
  // Khởi tạo editor với React và history
  const editor = useMemo(() => withHistory(withReact(createEditor())), []);

  // Khởi tạo giá trị ban đầu cho editor
  const [value, setValue] = useState([
    {
      type: 'paragraph',
      children: [
        { text: 'This is a rich text editor using Slate.js' },
      ],
    },
  ]);

  // Hàm xử lý khi giá trị thay đổi
  const handleChange = (newValue) => {
    setValue(newValue);
  };

  // Hàm xử lý in đậm
  const toggleBold = () => {
    const isBold = isMarkActive(editor, 'bold');
    toggleMark(editor, 'bold', !isBold);
  };

  // Hàm xử lý in nghiêng
  const toggleItalic = () => {
    const isItalic = isMarkActive(editor, 'italic');
    toggleMark(editor, 'italic', !isItalic);
  };

  // Kiểm tra kiểu văn bản hiện tại
  const isMarkActive = (editor, format) => {
    const marks = Editor.marks(editor);
    return marks ? marks[format] === true : false;
  };

  // Thêm hoặc loại bỏ kiểu văn bản
  const toggleMark = (editor, format, value) => {
    const isActive = isMarkActive(editor, format);
    if (isActive !== value) {
      Transforms.setNodes(editor, { [format]: value });
    } else {
      Transforms.removeMark(editor, format);
    }
  };

  return (
    <Container>
      <Box sx={{ my: 3 }}>
        <Typography variant="h5">Rich Text Editor with Slate</Typography>

        <Box sx={{ display: 'flex', mb: 2 }}>
          <Button variant="contained" color="primary" onClick={toggleBold}>
            Bold
          </Button>
          <Button variant="contained" color="primary" onClick={toggleItalic} sx={{ ml: 2 }}>
            Italic
          </Button>
        </Box>

        <Slate editor={editor} initialValue={value} onChange={handleChange}>
          <Editable />
        </Slate>
      </Box>
    </Container >
  );
};

export default RichTextEditor;
