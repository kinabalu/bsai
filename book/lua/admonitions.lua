-- Edit this with labels and attributes for admonition div classes
local cls_data = {
  note = {
    label = 'Note',
    label_attrs = {
      ['custom-style'] = 'NoteLabel'
    },
    text_attrs = {
      ['custom-style'] = 'NoteText'
    }
  },
  warning = {
    label = 'Warning',
    label_attrs = {
      ['custom-style'] = 'NoteLabel'
    },
    text_attrs = {
      ['custom-style'] = 'NoteText'
    }
  }
}

-- Get the pandoc library under a shorter name
local p = assert(pandoc, "Cannot find the pandoc library")
if not ('table' == type(p)) then
  error("Expected variable pandoc to be table")
end

-- Create the label divs
for cls, data in pairs(cls_data) do
  data.label = p.Div({
    p.Para({ p.Str(data.label) })
  }, data.label_attrs)
end

-- The filter function
function Div (div)
  for _, cls in ipairs(div.classes) do
    local data = cls_data[cls] -- get data if any
    if data then -- if this is an admonition class
      -- Set the attributes on the div
      for name, val in pairs(data.text_attrs) do
        div.attributes[name] = val
      end
      -- Return the data and the div
      return { data.label:clone(), div }
    end
  end
  -- If no class matches
  return nil
end
