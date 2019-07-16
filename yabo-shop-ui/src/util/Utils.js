export default {
  stringFormat: (s, ...a) => s.replace(/\{(\d+)\}/g, (m, n) => a[n] || m)
};
