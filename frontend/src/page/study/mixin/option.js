const editorEvents = ['load', 'change', 'stateChange', 'focus', 'blur'];
const defaultValueMap = {
  initialEditType: 'markdown',
  initialValue: '',
  height: '500px',
  previewStyle: 'vertical',
  placeholder: '내용을 입력해주세요.',
  
};

export const optionsMixin = {
  data() {
    const eventOptions = {};
    editorEvents.forEach(event => {
      eventOptions[event] = (...args) => {
        this.$emit(event, ...args);
      };
    });
    const options = {
      ...this.options,
      initialEditType: this.initialEditType,
      initialValue: this.initialValue,
      height: this.height,
      previewStyle: this.previewStyle,
      placeholder: this.placeholder,
      events: eventOptions
    };
    Object.keys(defaultValueMap).forEach(key => {
      if (!options[key]) {
        options[key] = defaultValueMap[key];
      }
    });

    return { editor: null, computedOptions: options };
  },

  
  methods: {
    invoke(methodName, ...args) {
      let result = null;
      if (this.editor[methodName]) {
        result = this.editor[methodName](...args);
      }

      return result;
    }
  },
  destroyed() {
    editorEvents.forEach(event => {
      this.editor.off(event);
    });
    this.editor.remove();
  }
};