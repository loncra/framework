function me(t, e) {
  return function() {
    return t.apply(e, arguments);
  };
}
const { toString: ke } = Object.prototype, { getPrototypeOf: Q } = Object, M = ((t) => (e) => {
  const n = ke.call(e);
  return t[n] || (t[n] = n.slice(8, -1).toLowerCase());
})(/* @__PURE__ */ Object.create(null)), S = (t) => (t = t.toLowerCase(), (e) => M(e) === t), H = (t) => (e) => typeof e === t, { isArray: x } = Array, N = H("undefined");
function De(t) {
  return t !== null && !N(t) && t.constructor !== null && !N(t.constructor) && w(t.constructor.isBuffer) && t.constructor.isBuffer(t);
}
const ge = S("ArrayBuffer");
function Ue(t) {
  let e;
  return typeof ArrayBuffer < "u" && ArrayBuffer.isView ? e = ArrayBuffer.isView(t) : e = t && t.buffer && ge(t.buffer), e;
}
const Me = H("string"), w = H("function"), ye = H("number"), V = (t) => t !== null && typeof t == "object", He = (t) => t === !0 || t === !1, I = (t) => {
  if (M(t) !== "object")
    return !1;
  const e = Q(t);
  return (e === null || e === Object.prototype || Object.getPrototypeOf(e) === null) && !(Symbol.toStringTag in t) && !(Symbol.iterator in t);
}, Ve = S("Date"), je = S("File"), qe = S("Blob"), ze = S("FileList"), We = (t) => V(t) && w(t.pipe), Xe = (t) => {
  let e;
  return t && (typeof FormData == "function" && t instanceof FormData || w(t.append) && ((e = M(t)) === "formdata" || // detect form-data instance
  e === "object" && w(t.toString) && t.toString() === "[object FormData]"));
}, $e = S("URLSearchParams"), Je = (t) => t.trim ? t.trim() : t.replace(/^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g, "");
function B(t, e, { allOwnKeys: n = !1 } = {}) {
  if (t === null || typeof t > "u")
    return;
  let i, r;
  if (typeof t != "object" && (t = [t]), x(t))
    for (i = 0, r = t.length; i < r; i++)
      e.call(null, t[i], i, t);
  else {
    const s = n ? Object.getOwnPropertyNames(t) : Object.keys(t), o = s.length;
    let c;
    for (i = 0; i < o; i++)
      c = s[i], e.call(null, t[c], c, t);
  }
}
function Ee(t, e) {
  e = e.toLowerCase();
  const n = Object.keys(t);
  let i = n.length, r;
  for (; i-- > 0; )
    if (r = n[i], e === r.toLowerCase())
      return r;
  return null;
}
const we = (() => typeof globalThis < "u" ? globalThis : typeof self < "u" ? self : typeof window < "u" ? window : global)(), be = (t) => !N(t) && t !== we;
function J() {
  const { caseless: t } = be(this) && this || {}, e = {}, n = (i, r) => {
    const s = t && Ee(e, r) || r;
    I(e[s]) && I(i) ? e[s] = J(e[s], i) : I(i) ? e[s] = J({}, i) : x(i) ? e[s] = i.slice() : e[s] = i;
  };
  for (let i = 0, r = arguments.length; i < r; i++)
    arguments[i] && B(arguments[i], n);
  return e;
}
const Ye = (t, e, n, { allOwnKeys: i } = {}) => (B(e, (r, s) => {
  n && w(r) ? t[s] = me(r, n) : t[s] = r;
}, { allOwnKeys: i }), t), Ke = (t) => (t.charCodeAt(0) === 65279 && (t = t.slice(1)), t), Ge = (t, e, n, i) => {
  t.prototype = Object.create(e.prototype, i), t.prototype.constructor = t, Object.defineProperty(t, "super", {
    value: e.prototype
  }), n && Object.assign(t.prototype, n);
}, Qe = (t, e, n, i) => {
  let r, s, o;
  const c = {};
  if (e = e || {}, t == null)
    return e;
  do {
    for (r = Object.getOwnPropertyNames(t), s = r.length; s-- > 0; )
      o = r[s], (!i || i(o, t, e)) && !c[o] && (e[o] = t[o], c[o] = !0);
    t = n !== !1 && Q(t);
  } while (t && (!n || n(t, e)) && t !== Object.prototype);
  return e;
}, Ze = (t, e, n) => {
  t = String(t), (n === void 0 || n > t.length) && (n = t.length), n -= e.length;
  const i = t.indexOf(e, n);
  return i !== -1 && i === n;
}, et = (t) => {
  if (!t)
    return null;
  if (x(t))
    return t;
  let e = t.length;
  if (!ye(e))
    return null;
  const n = new Array(e);
  for (; e-- > 0; )
    n[e] = t[e];
  return n;
}, tt = ((t) => (e) => t && e instanceof t)(typeof Uint8Array < "u" && Q(Uint8Array)), nt = (t, e) => {
  const i = (t && t[Symbol.iterator]).call(t);
  let r;
  for (; (r = i.next()) && !r.done; ) {
    const s = r.value;
    e.call(t, s[0], s[1]);
  }
}, it = (t, e) => {
  let n;
  const i = [];
  for (; (n = t.exec(e)) !== null; )
    i.push(n);
  return i;
}, rt = S("HTMLFormElement"), st = (t) => t.toLowerCase().replace(
  /[-_\s]([a-z\d])(\w*)/g,
  function(n, i, r) {
    return i.toUpperCase() + r;
  }
), re = (({ hasOwnProperty: t }) => (e, n) => t.call(e, n))(Object.prototype), ot = S("RegExp"), Te = (t, e) => {
  const n = Object.getOwnPropertyDescriptors(t), i = {};
  B(n, (r, s) => {
    let o;
    (o = e(r, s, t)) !== !1 && (i[s] = o || r);
  }), Object.defineProperties(t, i);
}, at = (t) => {
  Te(t, (e, n) => {
    if (w(t) && ["arguments", "caller", "callee"].indexOf(n) !== -1)
      return !1;
    const i = t[n];
    if (w(i)) {
      if (e.enumerable = !1, "writable" in e) {
        e.writable = !1;
        return;
      }
      e.set || (e.set = () => {
        throw Error("Can not rewrite read-only method '" + n + "'");
      });
    }
  });
}, ct = (t, e) => {
  const n = {}, i = (r) => {
    r.forEach((s) => {
      n[s] = !0;
    });
  };
  return x(t) ? i(t) : i(String(t).split(e)), n;
}, lt = () => {
}, ut = (t, e) => (t = +t, Number.isFinite(t) ? t : e), z = "abcdefghijklmnopqrstuvwxyz", se = "0123456789", Se = {
  DIGIT: se,
  ALPHA: z,
  ALPHA_DIGIT: z + z.toUpperCase() + se
}, dt = (t = 16, e = Se.ALPHA_DIGIT) => {
  let n = "";
  const { length: i } = e;
  for (; t--; )
    n += e[Math.random() * i | 0];
  return n;
};
function ft(t) {
  return !!(t && w(t.append) && t[Symbol.toStringTag] === "FormData" && t[Symbol.iterator]);
}
const ht = (t) => {
  const e = new Array(10), n = (i, r) => {
    if (V(i)) {
      if (e.indexOf(i) >= 0)
        return;
      if (!("toJSON" in i)) {
        e[r] = i;
        const s = x(i) ? [] : {};
        return B(i, (o, c) => {
          const d = n(o, r + 1);
          !N(d) && (s[c] = d);
        }), e[r] = void 0, s;
      }
    }
    return i;
  };
  return n(t, 0);
}, pt = S("AsyncFunction"), mt = (t) => t && (V(t) || w(t)) && w(t.then) && w(t.catch), a = {
  isArray: x,
  isArrayBuffer: ge,
  isBuffer: De,
  isFormData: Xe,
  isArrayBufferView: Ue,
  isString: Me,
  isNumber: ye,
  isBoolean: He,
  isObject: V,
  isPlainObject: I,
  isUndefined: N,
  isDate: Ve,
  isFile: je,
  isBlob: qe,
  isRegExp: ot,
  isFunction: w,
  isStream: We,
  isURLSearchParams: $e,
  isTypedArray: tt,
  isFileList: ze,
  forEach: B,
  merge: J,
  extend: Ye,
  trim: Je,
  stripBOM: Ke,
  inherits: Ge,
  toFlatObject: Qe,
  kindOf: M,
  kindOfTest: S,
  endsWith: Ze,
  toArray: et,
  forEachEntry: nt,
  matchAll: it,
  isHTMLForm: rt,
  hasOwnProperty: re,
  hasOwnProp: re,
  // an alias to avoid ESLint no-prototype-builtins detection
  reduceDescriptors: Te,
  freezeMethods: at,
  toObjectSet: ct,
  toCamelCase: st,
  noop: lt,
  toFiniteNumber: ut,
  findKey: Ee,
  global: we,
  isContextDefined: be,
  ALPHABET: Se,
  generateString: dt,
  isSpecCompliantForm: ft,
  toJSONObject: ht,
  isAsyncFn: pt,
  isThenable: mt
};
function m(t, e, n, i, r) {
  Error.call(this), Error.captureStackTrace ? Error.captureStackTrace(this, this.constructor) : this.stack = new Error().stack, this.message = t, this.name = "AxiosError", e && (this.code = e), n && (this.config = n), i && (this.request = i), r && (this.response = r);
}
a.inherits(m, Error, {
  toJSON: function() {
    return {
      // Standard
      message: this.message,
      name: this.name,
      // Microsoft
      description: this.description,
      number: this.number,
      // Mozilla
      fileName: this.fileName,
      lineNumber: this.lineNumber,
      columnNumber: this.columnNumber,
      stack: this.stack,
      // Axios
      config: a.toJSONObject(this.config),
      code: this.code,
      status: this.response && this.response.status ? this.response.status : null
    };
  }
});
const ve = m.prototype, Ae = {};
[
  "ERR_BAD_OPTION_VALUE",
  "ERR_BAD_OPTION",
  "ECONNABORTED",
  "ETIMEDOUT",
  "ERR_NETWORK",
  "ERR_FR_TOO_MANY_REDIRECTS",
  "ERR_DEPRECATED",
  "ERR_BAD_RESPONSE",
  "ERR_BAD_REQUEST",
  "ERR_CANCELED",
  "ERR_NOT_SUPPORT",
  "ERR_INVALID_URL"
  // eslint-disable-next-line func-names
].forEach((t) => {
  Ae[t] = { value: t };
});
Object.defineProperties(m, Ae);
Object.defineProperty(ve, "isAxiosError", { value: !0 });
m.from = (t, e, n, i, r, s) => {
  const o = Object.create(ve);
  return a.toFlatObject(t, o, function(d) {
    return d !== Error.prototype;
  }, (c) => c !== "isAxiosError"), m.call(o, t.message, e, n, i, r), o.cause = t, o.name = t.name, s && Object.assign(o, s), o;
};
const gt = null;
function Y(t) {
  return a.isPlainObject(t) || a.isArray(t);
}
function Re(t) {
  return a.endsWith(t, "[]") ? t.slice(0, -2) : t;
}
function oe(t, e, n) {
  return t ? t.concat(e).map(function(r, s) {
    return r = Re(r), !n && s ? "[" + r + "]" : r;
  }).join(n ? "." : "") : e;
}
function yt(t) {
  return a.isArray(t) && !t.some(Y);
}
const Et = a.toFlatObject(a, {}, null, function(e) {
  return /^is[A-Z]/.test(e);
});
function j(t, e, n) {
  if (!a.isObject(t))
    throw new TypeError("target must be an object");
  e = e || new FormData(), n = a.toFlatObject(n, {
    metaTokens: !0,
    dots: !1,
    indexes: !1
  }, !1, function(p, v) {
    return !a.isUndefined(v[p]);
  });
  const i = n.metaTokens, r = n.visitor || u, s = n.dots, o = n.indexes, d = (n.Blob || typeof Blob < "u" && Blob) && a.isSpecCompliantForm(e);
  if (!a.isFunction(r))
    throw new TypeError("visitor must be a function");
  function l(f) {
    if (f === null)
      return "";
    if (a.isDate(f))
      return f.toISOString();
    if (!d && a.isBlob(f))
      throw new m("Blob is not supported. Use a Buffer instead.");
    return a.isArrayBuffer(f) || a.isTypedArray(f) ? d && typeof Blob == "function" ? new Blob([f]) : Buffer.from(f) : f;
  }
  function u(f, p, v) {
    let b = f;
    if (f && !v && typeof f == "object") {
      if (a.endsWith(p, "{}"))
        p = i ? p : p.slice(0, -2), f = JSON.stringify(f);
      else if (a.isArray(f) && yt(f) || (a.isFileList(f) || a.endsWith(p, "[]")) && (b = a.toArray(f)))
        return p = Re(p), b.forEach(function(P, Fe) {
          !(a.isUndefined(P) || P === null) && e.append(
            // eslint-disable-next-line no-nested-ternary
            o === !0 ? oe([p], Fe, s) : o === null ? p : p + "[]",
            l(P)
          );
        }), !1;
    }
    return Y(f) ? !0 : (e.append(oe(v, p, s), l(f)), !1);
  }
  const h = [], E = Object.assign(Et, {
    defaultVisitor: u,
    convertValue: l,
    isVisitable: Y
  });
  function g(f, p) {
    if (!a.isUndefined(f)) {
      if (h.indexOf(f) !== -1)
        throw Error("Circular reference detected in " + p.join("."));
      h.push(f), a.forEach(f, function(b, O) {
        (!(a.isUndefined(b) || b === null) && r.call(
          e,
          b,
          a.isString(O) ? O.trim() : O,
          p,
          E
        )) === !0 && g(b, p ? p.concat(O) : [O]);
      }), h.pop();
    }
  }
  if (!a.isObject(t))
    throw new TypeError("data must be an object");
  return g(t), e;
}
function ae(t) {
  const e = {
    "!": "%21",
    "'": "%27",
    "(": "%28",
    ")": "%29",
    "~": "%7E",
    "%20": "+",
    "%00": "\0"
  };
  return encodeURIComponent(t).replace(/[!'()~]|%20|%00/g, function(i) {
    return e[i];
  });
}
function Z(t, e) {
  this._pairs = [], t && j(t, this, e);
}
const Oe = Z.prototype;
Oe.append = function(e, n) {
  this._pairs.push([e, n]);
};
Oe.toString = function(e) {
  const n = e ? function(i) {
    return e.call(this, i, ae);
  } : ae;
  return this._pairs.map(function(r) {
    return n(r[0]) + "=" + n(r[1]);
  }, "").join("&");
};
function wt(t) {
  return encodeURIComponent(t).replace(/%3A/gi, ":").replace(/%24/g, "$").replace(/%2C/gi, ",").replace(/%20/g, "+").replace(/%5B/gi, "[").replace(/%5D/gi, "]");
}
function Ce(t, e, n) {
  if (!e)
    return t;
  const i = n && n.encode || wt, r = n && n.serialize;
  let s;
  if (r ? s = r(e, n) : s = a.isURLSearchParams(e) ? e.toString() : new Z(e, n).toString(i), s) {
    const o = t.indexOf("#");
    o !== -1 && (t = t.slice(0, o)), t += (t.indexOf("?") === -1 ? "?" : "&") + s;
  }
  return t;
}
class bt {
  constructor() {
    this.handlers = [];
  }
  /**
   * Add a new interceptor to the stack
   *
   * @param {Function} fulfilled The function to handle `then` for a `Promise`
   * @param {Function} rejected The function to handle `reject` for a `Promise`
   *
   * @return {Number} An ID used to remove interceptor later
   */
  use(e, n, i) {
    return this.handlers.push({
      fulfilled: e,
      rejected: n,
      synchronous: i ? i.synchronous : !1,
      runWhen: i ? i.runWhen : null
    }), this.handlers.length - 1;
  }
  /**
   * Remove an interceptor from the stack
   *
   * @param {Number} id The ID that was returned by `use`
   *
   * @returns {Boolean} `true` if the interceptor was removed, `false` otherwise
   */
  eject(e) {
    this.handlers[e] && (this.handlers[e] = null);
  }
  /**
   * Clear all interceptors from the stack
   *
   * @returns {void}
   */
  clear() {
    this.handlers && (this.handlers = []);
  }
  /**
   * Iterate over all the registered interceptors
   *
   * This method is particularly useful for skipping over any
   * interceptors that may have become `null` calling `eject`.
   *
   * @param {Function} fn The function to call for each interceptor
   *
   * @returns {void}
   */
  forEach(e) {
    a.forEach(this.handlers, function(i) {
      i !== null && e(i);
    });
  }
}
const ce = bt, xe = {
  silentJSONParsing: !0,
  forcedJSONParsing: !0,
  clarifyTimeoutError: !1
}, Tt = typeof URLSearchParams < "u" ? URLSearchParams : Z, St = typeof FormData < "u" ? FormData : null, vt = typeof Blob < "u" ? Blob : null, At = (() => {
  let t;
  return typeof navigator < "u" && ((t = navigator.product) === "ReactNative" || t === "NativeScript" || t === "NS") ? !1 : typeof window < "u" && typeof document < "u";
})(), Rt = (() => typeof WorkerGlobalScope < "u" && // eslint-disable-next-line no-undef
self instanceof WorkerGlobalScope && typeof self.importScripts == "function")(), T = {
  isBrowser: !0,
  classes: {
    URLSearchParams: Tt,
    FormData: St,
    Blob: vt
  },
  isStandardBrowserEnv: At,
  isStandardBrowserWebWorkerEnv: Rt,
  protocols: ["http", "https", "file", "blob", "url", "data"]
};
function Ot(t, e) {
  return j(t, new T.classes.URLSearchParams(), Object.assign({
    visitor: function(n, i, r, s) {
      return T.isNode && a.isBuffer(n) ? (this.append(i, n.toString("base64")), !1) : s.defaultVisitor.apply(this, arguments);
    }
  }, e));
}
function Ct(t) {
  return a.matchAll(/\w+|\[(\w*)]/g, t).map((e) => e[0] === "[]" ? "" : e[1] || e[0]);
}
function xt(t) {
  const e = {}, n = Object.keys(t);
  let i;
  const r = n.length;
  let s;
  for (i = 0; i < r; i++)
    s = n[i], e[s] = t[s];
  return e;
}
function _e(t) {
  function e(n, i, r, s) {
    let o = n[s++];
    const c = Number.isFinite(+o), d = s >= n.length;
    return o = !o && a.isArray(r) ? r.length : o, d ? (a.hasOwnProp(r, o) ? r[o] = [r[o], i] : r[o] = i, !c) : ((!r[o] || !a.isObject(r[o])) && (r[o] = []), e(n, i, r[o], s) && a.isArray(r[o]) && (r[o] = xt(r[o])), !c);
  }
  if (a.isFormData(t) && a.isFunction(t.entries)) {
    const n = {};
    return a.forEachEntry(t, (i, r) => {
      e(Ct(i), r, n, 0);
    }), n;
  }
  return null;
}
function _t(t, e, n) {
  if (a.isString(t))
    try {
      return (e || JSON.parse)(t), a.trim(t);
    } catch (i) {
      if (i.name !== "SyntaxError")
        throw i;
    }
  return (n || JSON.stringify)(t);
}
const ee = {
  transitional: xe,
  adapter: T.isNode ? "http" : "xhr",
  transformRequest: [function(e, n) {
    const i = n.getContentType() || "", r = i.indexOf("application/json") > -1, s = a.isObject(e);
    if (s && a.isHTMLForm(e) && (e = new FormData(e)), a.isFormData(e))
      return r && r ? JSON.stringify(_e(e)) : e;
    if (a.isArrayBuffer(e) || a.isBuffer(e) || a.isStream(e) || a.isFile(e) || a.isBlob(e))
      return e;
    if (a.isArrayBufferView(e))
      return e.buffer;
    if (a.isURLSearchParams(e))
      return n.setContentType("application/x-www-form-urlencoded;charset=utf-8", !1), e.toString();
    let c;
    if (s) {
      if (i.indexOf("application/x-www-form-urlencoded") > -1)
        return Ot(e, this.formSerializer).toString();
      if ((c = a.isFileList(e)) || i.indexOf("multipart/form-data") > -1) {
        const d = this.env && this.env.FormData;
        return j(
          c ? { "files[]": e } : e,
          d && new d(),
          this.formSerializer
        );
      }
    }
    return s || r ? (n.setContentType("application/json", !1), _t(e)) : e;
  }],
  transformResponse: [function(e) {
    const n = this.transitional || ee.transitional, i = n && n.forcedJSONParsing, r = this.responseType === "json";
    if (e && a.isString(e) && (i && !this.responseType || r)) {
      const o = !(n && n.silentJSONParsing) && r;
      try {
        return JSON.parse(e);
      } catch (c) {
        if (o)
          throw c.name === "SyntaxError" ? m.from(c, m.ERR_BAD_RESPONSE, this, null, this.response) : c;
      }
    }
    return e;
  }],
  /**
   * A timeout in milliseconds to abort a request. If set to 0 (default) a
   * timeout is not created.
   */
  timeout: 0,
  xsrfCookieName: "XSRF-TOKEN",
  xsrfHeaderName: "X-XSRF-TOKEN",
  maxContentLength: -1,
  maxBodyLength: -1,
  env: {
    FormData: T.classes.FormData,
    Blob: T.classes.Blob
  },
  validateStatus: function(e) {
    return e >= 200 && e < 300;
  },
  headers: {
    common: {
      Accept: "application/json, text/plain, */*",
      "Content-Type": void 0
    }
  }
};
a.forEach(["delete", "get", "head", "post", "put", "patch"], (t) => {
  ee.headers[t] = {};
});
const te = ee, Nt = a.toObjectSet([
  "age",
  "authorization",
  "content-length",
  "content-type",
  "etag",
  "expires",
  "from",
  "host",
  "if-modified-since",
  "if-unmodified-since",
  "last-modified",
  "location",
  "max-forwards",
  "proxy-authorization",
  "referer",
  "retry-after",
  "user-agent"
]), Bt = (t) => {
  const e = {};
  let n, i, r;
  return t && t.split(`
`).forEach(function(o) {
    r = o.indexOf(":"), n = o.substring(0, r).trim().toLowerCase(), i = o.substring(r + 1).trim(), !(!n || e[n] && Nt[n]) && (n === "set-cookie" ? e[n] ? e[n].push(i) : e[n] = [i] : e[n] = e[n] ? e[n] + ", " + i : i);
  }), e;
}, le = Symbol("internals");
function _(t) {
  return t && String(t).trim().toLowerCase();
}
function F(t) {
  return t === !1 || t == null ? t : a.isArray(t) ? t.map(F) : String(t);
}
function Lt(t) {
  const e = /* @__PURE__ */ Object.create(null), n = /([^\s,;=]+)\s*(?:=\s*([^,;]+))?/g;
  let i;
  for (; i = n.exec(t); )
    e[i[1]] = i[2];
  return e;
}
const Pt = (t) => /^[-_a-zA-Z0-9^`|~,!#$%&'*+.]+$/.test(t.trim());
function W(t, e, n, i, r) {
  if (a.isFunction(i))
    return i.call(this, e, n);
  if (r && (e = n), !!a.isString(e)) {
    if (a.isString(i))
      return e.indexOf(i) !== -1;
    if (a.isRegExp(i))
      return i.test(e);
  }
}
function It(t) {
  return t.trim().toLowerCase().replace(/([a-z\d])(\w*)/g, (e, n, i) => n.toUpperCase() + i);
}
function Ft(t, e) {
  const n = a.toCamelCase(" " + e);
  ["get", "set", "has"].forEach((i) => {
    Object.defineProperty(t, i + n, {
      value: function(r, s, o) {
        return this[i].call(this, e, r, s, o);
      },
      configurable: !0
    });
  });
}
class q {
  constructor(e) {
    e && this.set(e);
  }
  set(e, n, i) {
    const r = this;
    function s(c, d, l) {
      const u = _(d);
      if (!u)
        throw new Error("header name must be a non-empty string");
      const h = a.findKey(r, u);
      (!h || r[h] === void 0 || l === !0 || l === void 0 && r[h] !== !1) && (r[h || d] = F(c));
    }
    const o = (c, d) => a.forEach(c, (l, u) => s(l, u, d));
    return a.isPlainObject(e) || e instanceof this.constructor ? o(e, n) : a.isString(e) && (e = e.trim()) && !Pt(e) ? o(Bt(e), n) : e != null && s(n, e, i), this;
  }
  get(e, n) {
    if (e = _(e), e) {
      const i = a.findKey(this, e);
      if (i) {
        const r = this[i];
        if (!n)
          return r;
        if (n === !0)
          return Lt(r);
        if (a.isFunction(n))
          return n.call(this, r, i);
        if (a.isRegExp(n))
          return n.exec(r);
        throw new TypeError("parser must be boolean|regexp|function");
      }
    }
  }
  has(e, n) {
    if (e = _(e), e) {
      const i = a.findKey(this, e);
      return !!(i && this[i] !== void 0 && (!n || W(this, this[i], i, n)));
    }
    return !1;
  }
  delete(e, n) {
    const i = this;
    let r = !1;
    function s(o) {
      if (o = _(o), o) {
        const c = a.findKey(i, o);
        c && (!n || W(i, i[c], c, n)) && (delete i[c], r = !0);
      }
    }
    return a.isArray(e) ? e.forEach(s) : s(e), r;
  }
  clear(e) {
    const n = Object.keys(this);
    let i = n.length, r = !1;
    for (; i--; ) {
      const s = n[i];
      (!e || W(this, this[s], s, e, !0)) && (delete this[s], r = !0);
    }
    return r;
  }
  normalize(e) {
    const n = this, i = {};
    return a.forEach(this, (r, s) => {
      const o = a.findKey(i, s);
      if (o) {
        n[o] = F(r), delete n[s];
        return;
      }
      const c = e ? It(s) : String(s).trim();
      c !== s && delete n[s], n[c] = F(r), i[c] = !0;
    }), this;
  }
  concat(...e) {
    return this.constructor.concat(this, ...e);
  }
  toJSON(e) {
    const n = /* @__PURE__ */ Object.create(null);
    return a.forEach(this, (i, r) => {
      i != null && i !== !1 && (n[r] = e && a.isArray(i) ? i.join(", ") : i);
    }), n;
  }
  [Symbol.iterator]() {
    return Object.entries(this.toJSON())[Symbol.iterator]();
  }
  toString() {
    return Object.entries(this.toJSON()).map(([e, n]) => e + ": " + n).join(`
`);
  }
  get [Symbol.toStringTag]() {
    return "AxiosHeaders";
  }
  static from(e) {
    return e instanceof this ? e : new this(e);
  }
  static concat(e, ...n) {
    const i = new this(e);
    return n.forEach((r) => i.set(r)), i;
  }
  static accessor(e) {
    const i = (this[le] = this[le] = {
      accessors: {}
    }).accessors, r = this.prototype;
    function s(o) {
      const c = _(o);
      i[c] || (Ft(r, o), i[c] = !0);
    }
    return a.isArray(e) ? e.forEach(s) : s(e), this;
  }
}
q.accessor(["Content-Type", "Content-Length", "Accept", "Accept-Encoding", "User-Agent", "Authorization"]);
a.reduceDescriptors(q.prototype, ({ value: t }, e) => {
  let n = e[0].toUpperCase() + e.slice(1);
  return {
    get: () => t,
    set(i) {
      this[n] = i;
    }
  };
});
a.freezeMethods(q);
const A = q;
function X(t, e) {
  const n = this || te, i = e || n, r = A.from(i.headers);
  let s = i.data;
  return a.forEach(t, function(c) {
    s = c.call(n, s, r.normalize(), e ? e.status : void 0);
  }), r.normalize(), s;
}
function Ne(t) {
  return !!(t && t.__CANCEL__);
}
function L(t, e, n) {
  m.call(this, t ?? "canceled", m.ERR_CANCELED, e, n), this.name = "CanceledError";
}
a.inherits(L, m, {
  __CANCEL__: !0
});
function kt(t, e, n) {
  const i = n.config.validateStatus;
  !n.status || !i || i(n.status) ? t(n) : e(new m(
    "Request failed with status code " + n.status,
    [m.ERR_BAD_REQUEST, m.ERR_BAD_RESPONSE][Math.floor(n.status / 100) - 4],
    n.config,
    n.request,
    n
  ));
}
const Dt = T.isStandardBrowserEnv ? (
  // Standard browser envs support document.cookie
  function() {
    return {
      write: function(n, i, r, s, o, c) {
        const d = [];
        d.push(n + "=" + encodeURIComponent(i)), a.isNumber(r) && d.push("expires=" + new Date(r).toGMTString()), a.isString(s) && d.push("path=" + s), a.isString(o) && d.push("domain=" + o), c === !0 && d.push("secure"), document.cookie = d.join("; ");
      },
      read: function(n) {
        const i = document.cookie.match(new RegExp("(^|;\\s*)(" + n + ")=([^;]*)"));
        return i ? decodeURIComponent(i[3]) : null;
      },
      remove: function(n) {
        this.write(n, "", Date.now() - 864e5);
      }
    };
  }()
) : (
  // Non standard browser env (web workers, react-native) lack needed support.
  function() {
    return {
      write: function() {
      },
      read: function() {
        return null;
      },
      remove: function() {
      }
    };
  }()
);
function Ut(t) {
  return /^([a-z][a-z\d+\-.]*:)?\/\//i.test(t);
}
function Mt(t, e) {
  return e ? t.replace(/\/+$/, "") + "/" + e.replace(/^\/+/, "") : t;
}
function Be(t, e) {
  return t && !Ut(e) ? Mt(t, e) : e;
}
const Ht = T.isStandardBrowserEnv ? (
  // Standard browser envs have full support of the APIs needed to test
  // whether the request URL is of the same origin as current location.
  function() {
    const e = /(msie|trident)/i.test(navigator.userAgent), n = document.createElement("a");
    let i;
    function r(s) {
      let o = s;
      return e && (n.setAttribute("href", o), o = n.href), n.setAttribute("href", o), {
        href: n.href,
        protocol: n.protocol ? n.protocol.replace(/:$/, "") : "",
        host: n.host,
        search: n.search ? n.search.replace(/^\?/, "") : "",
        hash: n.hash ? n.hash.replace(/^#/, "") : "",
        hostname: n.hostname,
        port: n.port,
        pathname: n.pathname.charAt(0) === "/" ? n.pathname : "/" + n.pathname
      };
    }
    return i = r(window.location.href), function(o) {
      const c = a.isString(o) ? r(o) : o;
      return c.protocol === i.protocol && c.host === i.host;
    };
  }()
) : (
  // Non standard browser envs (web workers, react-native) lack needed support.
  function() {
    return function() {
      return !0;
    };
  }()
);
function Vt(t) {
  const e = /^([-+\w]{1,25})(:?\/\/|:)/.exec(t);
  return e && e[1] || "";
}
function jt(t, e) {
  t = t || 10;
  const n = new Array(t), i = new Array(t);
  let r = 0, s = 0, o;
  return e = e !== void 0 ? e : 1e3, function(d) {
    const l = Date.now(), u = i[s];
    o || (o = l), n[r] = d, i[r] = l;
    let h = s, E = 0;
    for (; h !== r; )
      E += n[h++], h = h % t;
    if (r = (r + 1) % t, r === s && (s = (s + 1) % t), l - o < e)
      return;
    const g = u && l - u;
    return g ? Math.round(E * 1e3 / g) : void 0;
  };
}
function ue(t, e) {
  let n = 0;
  const i = jt(50, 250);
  return (r) => {
    const s = r.loaded, o = r.lengthComputable ? r.total : void 0, c = s - n, d = i(c), l = s <= o;
    n = s;
    const u = {
      loaded: s,
      total: o,
      progress: o ? s / o : void 0,
      bytes: c,
      rate: d || void 0,
      estimated: d && o && l ? (o - s) / d : void 0,
      event: r
    };
    u[e ? "download" : "upload"] = !0, t(u);
  };
}
const qt = typeof XMLHttpRequest < "u", zt = qt && function(t) {
  return new Promise(function(n, i) {
    let r = t.data;
    const s = A.from(t.headers).normalize(), o = t.responseType;
    let c;
    function d() {
      t.cancelToken && t.cancelToken.unsubscribe(c), t.signal && t.signal.removeEventListener("abort", c);
    }
    a.isFormData(r) && (T.isStandardBrowserEnv || T.isStandardBrowserWebWorkerEnv ? s.setContentType(!1) : s.setContentType("multipart/form-data;", !1));
    let l = new XMLHttpRequest();
    if (t.auth) {
      const g = t.auth.username || "", f = t.auth.password ? unescape(encodeURIComponent(t.auth.password)) : "";
      s.set("Authorization", "Basic " + btoa(g + ":" + f));
    }
    const u = Be(t.baseURL, t.url);
    l.open(t.method.toUpperCase(), Ce(u, t.params, t.paramsSerializer), !0), l.timeout = t.timeout;
    function h() {
      if (!l)
        return;
      const g = A.from(
        "getAllResponseHeaders" in l && l.getAllResponseHeaders()
      ), p = {
        data: !o || o === "text" || o === "json" ? l.responseText : l.response,
        status: l.status,
        statusText: l.statusText,
        headers: g,
        config: t,
        request: l
      };
      kt(function(b) {
        n(b), d();
      }, function(b) {
        i(b), d();
      }, p), l = null;
    }
    if ("onloadend" in l ? l.onloadend = h : l.onreadystatechange = function() {
      !l || l.readyState !== 4 || l.status === 0 && !(l.responseURL && l.responseURL.indexOf("file:") === 0) || setTimeout(h);
    }, l.onabort = function() {
      l && (i(new m("Request aborted", m.ECONNABORTED, t, l)), l = null);
    }, l.onerror = function() {
      i(new m("Network Error", m.ERR_NETWORK, t, l)), l = null;
    }, l.ontimeout = function() {
      let f = t.timeout ? "timeout of " + t.timeout + "ms exceeded" : "timeout exceeded";
      const p = t.transitional || xe;
      t.timeoutErrorMessage && (f = t.timeoutErrorMessage), i(new m(
        f,
        p.clarifyTimeoutError ? m.ETIMEDOUT : m.ECONNABORTED,
        t,
        l
      )), l = null;
    }, T.isStandardBrowserEnv) {
      const g = (t.withCredentials || Ht(u)) && t.xsrfCookieName && Dt.read(t.xsrfCookieName);
      g && s.set(t.xsrfHeaderName, g);
    }
    r === void 0 && s.setContentType(null), "setRequestHeader" in l && a.forEach(s.toJSON(), function(f, p) {
      l.setRequestHeader(p, f);
    }), a.isUndefined(t.withCredentials) || (l.withCredentials = !!t.withCredentials), o && o !== "json" && (l.responseType = t.responseType), typeof t.onDownloadProgress == "function" && l.addEventListener("progress", ue(t.onDownloadProgress, !0)), typeof t.onUploadProgress == "function" && l.upload && l.upload.addEventListener("progress", ue(t.onUploadProgress)), (t.cancelToken || t.signal) && (c = (g) => {
      l && (i(!g || g.type ? new L(null, t, l) : g), l.abort(), l = null);
    }, t.cancelToken && t.cancelToken.subscribe(c), t.signal && (t.signal.aborted ? c() : t.signal.addEventListener("abort", c)));
    const E = Vt(u);
    if (E && T.protocols.indexOf(E) === -1) {
      i(new m("Unsupported protocol " + E + ":", m.ERR_BAD_REQUEST, t));
      return;
    }
    l.send(r || null);
  });
}, k = {
  http: gt,
  xhr: zt
};
a.forEach(k, (t, e) => {
  if (t) {
    try {
      Object.defineProperty(t, "name", { value: e });
    } catch {
    }
    Object.defineProperty(t, "adapterName", { value: e });
  }
});
const Le = {
  getAdapter: (t) => {
    t = a.isArray(t) ? t : [t];
    const { length: e } = t;
    let n, i;
    for (let r = 0; r < e && (n = t[r], !(i = a.isString(n) ? k[n.toLowerCase()] : n)); r++)
      ;
    if (!i)
      throw i === !1 ? new m(
        `Adapter ${n} is not supported by the environment`,
        "ERR_NOT_SUPPORT"
      ) : new Error(
        a.hasOwnProp(k, n) ? `Adapter '${n}' is not available in the build` : `Unknown adapter '${n}'`
      );
    if (!a.isFunction(i))
      throw new TypeError("adapter is not a function");
    return i;
  },
  adapters: k
};
function $(t) {
  if (t.cancelToken && t.cancelToken.throwIfRequested(), t.signal && t.signal.aborted)
    throw new L(null, t);
}
function de(t) {
  return $(t), t.headers = A.from(t.headers), t.data = X.call(
    t,
    t.transformRequest
  ), ["post", "put", "patch"].indexOf(t.method) !== -1 && t.headers.setContentType("application/x-www-form-urlencoded", !1), Le.getAdapter(t.adapter || te.adapter)(t).then(function(i) {
    return $(t), i.data = X.call(
      t,
      t.transformResponse,
      i
    ), i.headers = A.from(i.headers), i;
  }, function(i) {
    return Ne(i) || ($(t), i && i.response && (i.response.data = X.call(
      t,
      t.transformResponse,
      i.response
    ), i.response.headers = A.from(i.response.headers))), Promise.reject(i);
  });
}
const fe = (t) => t instanceof A ? t.toJSON() : t;
function C(t, e) {
  e = e || {};
  const n = {};
  function i(l, u, h) {
    return a.isPlainObject(l) && a.isPlainObject(u) ? a.merge.call({ caseless: h }, l, u) : a.isPlainObject(u) ? a.merge({}, u) : a.isArray(u) ? u.slice() : u;
  }
  function r(l, u, h) {
    if (a.isUndefined(u)) {
      if (!a.isUndefined(l))
        return i(void 0, l, h);
    } else
      return i(l, u, h);
  }
  function s(l, u) {
    if (!a.isUndefined(u))
      return i(void 0, u);
  }
  function o(l, u) {
    if (a.isUndefined(u)) {
      if (!a.isUndefined(l))
        return i(void 0, l);
    } else
      return i(void 0, u);
  }
  function c(l, u, h) {
    if (h in e)
      return i(l, u);
    if (h in t)
      return i(void 0, l);
  }
  const d = {
    url: s,
    method: s,
    data: s,
    baseURL: o,
    transformRequest: o,
    transformResponse: o,
    paramsSerializer: o,
    timeout: o,
    timeoutMessage: o,
    withCredentials: o,
    adapter: o,
    responseType: o,
    xsrfCookieName: o,
    xsrfHeaderName: o,
    onUploadProgress: o,
    onDownloadProgress: o,
    decompress: o,
    maxContentLength: o,
    maxBodyLength: o,
    beforeRedirect: o,
    transport: o,
    httpAgent: o,
    httpsAgent: o,
    cancelToken: o,
    socketPath: o,
    responseEncoding: o,
    validateStatus: c,
    headers: (l, u) => r(fe(l), fe(u), !0)
  };
  return a.forEach(Object.keys(Object.assign({}, t, e)), function(u) {
    const h = d[u] || r, E = h(t[u], e[u], u);
    a.isUndefined(E) && h !== c || (n[u] = E);
  }), n;
}
const Pe = "1.5.0", ne = {};
["object", "boolean", "number", "function", "string", "symbol"].forEach((t, e) => {
  ne[t] = function(i) {
    return typeof i === t || "a" + (e < 1 ? "n " : " ") + t;
  };
});
const he = {};
ne.transitional = function(e, n, i) {
  function r(s, o) {
    return "[Axios v" + Pe + "] Transitional option '" + s + "'" + o + (i ? ". " + i : "");
  }
  return (s, o, c) => {
    if (e === !1)
      throw new m(
        r(o, " has been removed" + (n ? " in " + n : "")),
        m.ERR_DEPRECATED
      );
    return n && !he[o] && (he[o] = !0, console.warn(
      r(
        o,
        " has been deprecated since v" + n + " and will be removed in the near future"
      )
    )), e ? e(s, o, c) : !0;
  };
};
function Wt(t, e, n) {
  if (typeof t != "object")
    throw new m("options must be an object", m.ERR_BAD_OPTION_VALUE);
  const i = Object.keys(t);
  let r = i.length;
  for (; r-- > 0; ) {
    const s = i[r], o = e[s];
    if (o) {
      const c = t[s], d = c === void 0 || o(c, s, t);
      if (d !== !0)
        throw new m("option " + s + " must be " + d, m.ERR_BAD_OPTION_VALUE);
      continue;
    }
    if (n !== !0)
      throw new m("Unknown option " + s, m.ERR_BAD_OPTION);
  }
}
const K = {
  assertOptions: Wt,
  validators: ne
}, R = K.validators;
class U {
  constructor(e) {
    this.defaults = e, this.interceptors = {
      request: new ce(),
      response: new ce()
    };
  }
  /**
   * Dispatch a request
   *
   * @param {String|Object} configOrUrl The config specific for this request (merged with this.defaults)
   * @param {?Object} config
   *
   * @returns {Promise} The Promise to be fulfilled
   */
  request(e, n) {
    typeof e == "string" ? (n = n || {}, n.url = e) : n = e || {}, n = C(this.defaults, n);
    const { transitional: i, paramsSerializer: r, headers: s } = n;
    i !== void 0 && K.assertOptions(i, {
      silentJSONParsing: R.transitional(R.boolean),
      forcedJSONParsing: R.transitional(R.boolean),
      clarifyTimeoutError: R.transitional(R.boolean)
    }, !1), r != null && (a.isFunction(r) ? n.paramsSerializer = {
      serialize: r
    } : K.assertOptions(r, {
      encode: R.function,
      serialize: R.function
    }, !0)), n.method = (n.method || this.defaults.method || "get").toLowerCase();
    let o = s && a.merge(
      s.common,
      s[n.method]
    );
    s && a.forEach(
      ["delete", "get", "head", "post", "put", "patch", "common"],
      (f) => {
        delete s[f];
      }
    ), n.headers = A.concat(o, s);
    const c = [];
    let d = !0;
    this.interceptors.request.forEach(function(p) {
      typeof p.runWhen == "function" && p.runWhen(n) === !1 || (d = d && p.synchronous, c.unshift(p.fulfilled, p.rejected));
    });
    const l = [];
    this.interceptors.response.forEach(function(p) {
      l.push(p.fulfilled, p.rejected);
    });
    let u, h = 0, E;
    if (!d) {
      const f = [de.bind(this), void 0];
      for (f.unshift.apply(f, c), f.push.apply(f, l), E = f.length, u = Promise.resolve(n); h < E; )
        u = u.then(f[h++], f[h++]);
      return u;
    }
    E = c.length;
    let g = n;
    for (h = 0; h < E; ) {
      const f = c[h++], p = c[h++];
      try {
        g = f(g);
      } catch (v) {
        p.call(this, v);
        break;
      }
    }
    try {
      u = de.call(this, g);
    } catch (f) {
      return Promise.reject(f);
    }
    for (h = 0, E = l.length; h < E; )
      u = u.then(l[h++], l[h++]);
    return u;
  }
  getUri(e) {
    e = C(this.defaults, e);
    const n = Be(e.baseURL, e.url);
    return Ce(n, e.params, e.paramsSerializer);
  }
}
a.forEach(["delete", "get", "head", "options"], function(e) {
  U.prototype[e] = function(n, i) {
    return this.request(C(i || {}, {
      method: e,
      url: n,
      data: (i || {}).data
    }));
  };
});
a.forEach(["post", "put", "patch"], function(e) {
  function n(i) {
    return function(s, o, c) {
      return this.request(C(c || {}, {
        method: e,
        headers: i ? {
          "Content-Type": "multipart/form-data"
        } : {},
        url: s,
        data: o
      }));
    };
  }
  U.prototype[e] = n(), U.prototype[e + "Form"] = n(!0);
});
const D = U;
class ie {
  constructor(e) {
    if (typeof e != "function")
      throw new TypeError("executor must be a function.");
    let n;
    this.promise = new Promise(function(s) {
      n = s;
    });
    const i = this;
    this.promise.then((r) => {
      if (!i._listeners)
        return;
      let s = i._listeners.length;
      for (; s-- > 0; )
        i._listeners[s](r);
      i._listeners = null;
    }), this.promise.then = (r) => {
      let s;
      const o = new Promise((c) => {
        i.subscribe(c), s = c;
      }).then(r);
      return o.cancel = function() {
        i.unsubscribe(s);
      }, o;
    }, e(function(s, o, c) {
      i.reason || (i.reason = new L(s, o, c), n(i.reason));
    });
  }
  /**
   * Throws a `CanceledError` if cancellation has been requested.
   */
  throwIfRequested() {
    if (this.reason)
      throw this.reason;
  }
  /**
   * Subscribe to the cancel signal
   */
  subscribe(e) {
    if (this.reason) {
      e(this.reason);
      return;
    }
    this._listeners ? this._listeners.push(e) : this._listeners = [e];
  }
  /**
   * Unsubscribe from the cancel signal
   */
  unsubscribe(e) {
    if (!this._listeners)
      return;
    const n = this._listeners.indexOf(e);
    n !== -1 && this._listeners.splice(n, 1);
  }
  /**
   * Returns an object that contains a new `CancelToken` and a function that, when called,
   * cancels the `CancelToken`.
   */
  static source() {
    let e;
    return {
      token: new ie(function(r) {
        e = r;
      }),
      cancel: e
    };
  }
}
const Xt = ie;
function $t(t) {
  return function(n) {
    return t.apply(null, n);
  };
}
function Jt(t) {
  return a.isObject(t) && t.isAxiosError === !0;
}
const G = {
  Continue: 100,
  SwitchingProtocols: 101,
  Processing: 102,
  EarlyHints: 103,
  Ok: 200,
  Created: 201,
  Accepted: 202,
  NonAuthoritativeInformation: 203,
  NoContent: 204,
  ResetContent: 205,
  PartialContent: 206,
  MultiStatus: 207,
  AlreadyReported: 208,
  ImUsed: 226,
  MultipleChoices: 300,
  MovedPermanently: 301,
  Found: 302,
  SeeOther: 303,
  NotModified: 304,
  UseProxy: 305,
  Unused: 306,
  TemporaryRedirect: 307,
  PermanentRedirect: 308,
  BadRequest: 400,
  Unauthorized: 401,
  PaymentRequired: 402,
  Forbidden: 403,
  NotFound: 404,
  MethodNotAllowed: 405,
  NotAcceptable: 406,
  ProxyAuthenticationRequired: 407,
  RequestTimeout: 408,
  Conflict: 409,
  Gone: 410,
  LengthRequired: 411,
  PreconditionFailed: 412,
  PayloadTooLarge: 413,
  UriTooLong: 414,
  UnsupportedMediaType: 415,
  RangeNotSatisfiable: 416,
  ExpectationFailed: 417,
  ImATeapot: 418,
  MisdirectedRequest: 421,
  UnprocessableEntity: 422,
  Locked: 423,
  FailedDependency: 424,
  TooEarly: 425,
  UpgradeRequired: 426,
  PreconditionRequired: 428,
  TooManyRequests: 429,
  RequestHeaderFieldsTooLarge: 431,
  UnavailableForLegalReasons: 451,
  InternalServerError: 500,
  NotImplemented: 501,
  BadGateway: 502,
  ServiceUnavailable: 503,
  GatewayTimeout: 504,
  HttpVersionNotSupported: 505,
  VariantAlsoNegotiates: 506,
  InsufficientStorage: 507,
  LoopDetected: 508,
  NotExtended: 510,
  NetworkAuthenticationRequired: 511
};
Object.entries(G).forEach(([t, e]) => {
  G[e] = t;
});
const Yt = G;
function Ie(t) {
  const e = new D(t), n = me(D.prototype.request, e);
  return a.extend(n, D.prototype, e, { allOwnKeys: !0 }), a.extend(n, e, null, { allOwnKeys: !0 }), n.create = function(r) {
    return Ie(C(t, r));
  }, n;
}
const y = Ie(te);
y.Axios = D;
y.CanceledError = L;
y.CancelToken = Xt;
y.isCancel = Ne;
y.VERSION = Pe;
y.toFormData = j;
y.AxiosError = m;
y.Cancel = y.CanceledError;
y.all = function(e) {
  return Promise.all(e);
};
y.spread = $t;
y.isAxiosError = Jt;
y.mergeConfig = C;
y.AxiosHeaders = A;
y.formToJSON = (t) => _e(a.isHTMLForm(t) ? new FormData(t) : t);
y.getAdapter = Le.getAdapter;
y.HttpStatusCode = Yt;
y.default = y;
const pe = y;
class Kt {
  constructor(e) {
    this.config = e, this.config.postConfig = this.config.postConfig || {
      captchaParamName: "_tianaiCaptcha",
      tokenParamName: "_tianaiCaptchaToken"
    }, this.config.slider = this.config.slider || this.sliderConfig(), this.config.rotate = this.config.rotate || this.rotateConfig(), this.config.concat = this.config.concat || this.concatConfig(), this.config.dateFormat = this.config.dateFormat || "yyyy-MM-dd HH:mm:ss'", this.config.loadingText = this.config.loadingText || "", this.config.title = this.config.title || "安全验证", this.config.validText = this.config.validText || "", this.config.width = this.config.width || 400, this.config.showMerchantName = this.config.showMerchantName === void 0 ? !0 : this.config.showMerchantName, this.config.success = this.config.success || console.info, this.config.fail = this.config.fail || console.error, this.config.failRefreshCaptcha = this.config.failRefreshCaptcha === void 0 ? !0 : this.config.failRefreshCaptcha, this.config.baseUrl || (this.config.baseUrl = "$baseUrlToken$"), this.http = pe.create({ baseURL: this.config.baseUrl }), this.containerTemplate = '<div class="__tianai-container" id="tianai-container"></div>', this.contentTemplate = `
        <div class="__tianai-content" style="width:${this.config.width}px" id="tianai-content">
          <div class="__tianai-content-title" id="tianai-content-title">
            <div class="__tianai-content-title-text">${this.config.title}</div>
            <div class="__tianai-content-title-close" id="tianai-content-close-btn"></div>
          </div>
        </div>
    `, this.imageContentTemplate = `
        <div class="__tianai-content-image-bg" id="tianai-content-image-bg">
        </div>
        <div class="__tianai-content-target" id="tianai-image-content-target">
        </div>
    `, this.contentWrapper = '<div class="__tianai-content-image-wrapper" id="tianai-content-image-wrapper"></div>', this.contentOperate = `
        <div class="__tianai-slider-move">
          <div class="__tianai-slider-move-track" id="tianai-slider-move-track">
            
          </div>
          <div class="__tianai-btn __tianai-slider-move-btn" id="tianai-slider-move-btn">
          </div>
        </div>
        <div class="__tianai-operating">
        
          <div class="__tianai-operating-merchant" id="tianai-operating-merchant">
            
          </div>
          
          <div class="__tianai-operating-btn">
            <div class="__tianai-btn __tianai-operating-refresh-btn" id="tianai-operating-refresh-btn">
            </div>
            <div class="__tianai-btn __tianai-operating-close-btn" id="tianai-operating-close-btn">
            </div>
          </div>
        </div>
    `;
  }
  sliderConfig() {
    return {
      prompt: "滑动拼图块完成验证",
      onMove: this.sliderMove
    };
  }
  rotateConfig() {
    return {
      prompt: "滑动旋转角度完成验证",
      onMove: this.rotateMove
    };
  }
  concatConfig() {
    return {
      prompt: "滑动拼接图片完成验证",
      onMove: this.concatMove
    };
  }
  show() {
    if ({}.VITE_NODE_ENV !== "development")
      if (document.querySelector("link[id='tianai-captcha']"))
        this.doShow();
      else {
        let n = document.createElement("link");
        n.id = "tianai-captcha", n.type = "text/css", n.href = this.config.baseUrl + "/tianai-captcha.css", n.rel = "stylesheet", n.onerror = this.config.fail, n.onload = () => this.doShow(), document.body.appendChild(n);
      }
    else
      this.doShow();
  }
  doShow() {
    if (!document.getElementById("tianai-content")) {
      let n = this.config.container;
      n ? n = document.getElementById(this.config.container) : n = document.body, n.insertAdjacentHTML("beforeend", this.containerTemplate), n.lastElementChild.insertAdjacentHTML("beforeend", this.contentTemplate), this.fadeIn(n.lastElementChild), document.getElementById("tianai-content-close-btn").addEventListener("click", this.hide.bind(this));
    }
    this.generateCaptcha();
  }
  generateCaptcha(e = !0) {
    e && this.loading(this.config.loadingText);
    let n = this.config.postConfig.tokenParamName + "=" + this.config.token + "&captchaType=tianai&generateImageType=" + (this.config.generateType || "random");
    return this.http.get("/captcha/generateCaptcha?" + n).then((i) => this.doGenerateHtml(i.data.data)).catch((i) => {
      i.response.data ? (i.response.data, i.response.data.executeCode && i.response.data.executeCode === "10404" ? pe.get(this.config.baseUrl + "/captcha/generateToken?type=tianai").then((r) => {
        this.config.token = r.data.data.token.name, this.generateCaptcha(e);
      }) : this.config.fail(i)) : this.config.fail(i);
    });
  }
  doGenerateHtml(e) {
    var d;
    this.generateData = e, this.removeLoading();
    const n = document.getElementById("tianai-content-title");
    let i = document.getElementById("tianai-slider-move-btn");
    if (!i && (n.insertAdjacentHTML("afterend", this.contentOperate), i = document.getElementById("tianai-slider-move-btn"), i.addEventListener("mousedown", this.sliderDown.bind(this)), i.addEventListener("touchstart", this.sliderDown.bind(this)), document.getElementById("tianai-operating-refresh-btn").addEventListener("click", this.generateCaptcha.bind(this)), document.getElementById("tianai-operating-close-btn").addEventListener("click", this.hide.bind(this)), this.config.showMerchantName && e.merchantName)) {
      const h = document.getElementById("tianai-operating-merchant");
      h.innerHTML = e.merchantName || "";
    }
    let r = document.getElementById("tianai-content-image-wrapper");
    if (!r)
      n.insertAdjacentHTML("afterend", this.contentWrapper), r = document.getElementById("tianai-content-image-wrapper"), r.insertAdjacentHTML("beforeend", this.imageContentTemplate);
    else {
      const l = document.getElementById("tianai-content-image-bg");
      this.removeElement(l);
      const u = document.getElementById("tianai-image-content-target");
      this.removeElement(u), r.insertAdjacentHTML("beforeend", this.imageContentTemplate);
    }
    const s = document.getElementById("tianai-content-image-bg");
    let o = s.lastElementChild;
    o ? o.src = e.backgroundImage : s.insertAdjacentHTML("beforeend", `<img src="${e.backgroundImage}">`), this.validValue = {
      startX: 0,
      startY: 0,
      startTime: /* @__PURE__ */ new Date(),
      trackArr: []
    };
    const c = document.getElementById("tianai-image-content-target");
    if (["slider", "rotate"].includes(e.type)) {
      const l = document.getElementById("image-content-target-template");
      l ? l.src = e.templateImage : c.insertAdjacentHTML("beforeend", `<img src="${e.templateImage}" id="image-content-target-template">`);
    } else {
      const l = document.getElementById("image-content-target-template");
      l && this.fadeOut(l), c.style.backgroundImage = "url(" + e.backgroundImage + ")", c.style.backgroundPosition = "0px 0px";
      const u = e.backgroundImageHeight;
      c.style.height = u - e.data.viewData.randomY + "px";
    }
    document.getElementById("tianai-slider-move-track").innerHTML = ((d = this.config[e.type]) == null ? void 0 : d.prompt) || "拖动完成验证", setTimeout(() => {
      this.validValue.bgImageWidth = s.offsetWidth, this.validValue.bgImageHeight = s.offsetHeight, this.validValue.end = this.config.width - i.offsetWidth, e.type === "slider" ? (this.validValue.sliderImageWidth = c.lastElementChild.offsetWidth, this.validValue.sliderImageHeight = c.lastElementChild.offsetHeight) : e.type === "rotate" && (this.validValue.bgImageWidth = e.degree + e.randomX - this.validValue.bgImageHeight / 10);
    }, 500), c.classList.add(e.type.toLowerCase()), this.returnToActualPosition(i);
  }
  removeElement(e) {
    e && e.remove();
  }
  returnToActualPosition(e) {
    e.style.transform !== "" && e.style.transform !== "translate(0px, 0px)" && (e.style.transition = "transform 0.5s ease", e.style.transform = "translate(0px, 0px)", e.addEventListener("transitionend", (n) => e.style.transition = "none"));
  }
  fadeIn(e) {
    e.style.transition = "opacity 0.5s ease", e.style.opacity = "0", setTimeout(() => {
      e.style.opacity = "1", e.addEventListener("transitionend", () => e.style.transition = "none");
    }, 100);
  }
  fadeOut(e) {
    e.style.transition = "opacity 0.5s ease", e.style.opacity = "0", e.addEventListener("transitionend", () => e.remove());
  }
  formUrlEncoded(e, n, i) {
    let r = new URLSearchParams(), s = [];
    typeof n == "string" ? s.push(n) : s = n || [];
    for (let o in e) {
      if (s.includes(o))
        continue;
      let c = e[o];
      c != null && (i && (c = i(o, c)), Array.isArray(c) ? c.forEach((d) => r.append(o, d)) : r.append(o, c));
    }
    return r;
  }
  hide(e) {
    const n = document.getElementById("tianai-container");
    n && this.fadeOut(n);
  }
  sliderDown(e) {
    let n = e.originalEvent ? e.originalEvent.targetTouches : e.targetTouches, i = e.pageX, r = e.pageY;
    i === void 0 && (i = Math.round(n[0].pageX), r = Math.round(n[0].pageY)), this.validValue.startX = i, this.validValue.startY = r;
    const s = this.validValue.startX, o = this.validValue.startY, c = this.validValue.startTime;
    this.validValue.trackArr.push({
      x: s - i,
      y: o - r,
      type: "down",
      t: (/* @__PURE__ */ new Date()).getTime() - c.getTime()
    }), this.validValue.mouseMoveFunction = this.move.bind(this), this.validValue.mouseUpFunction = this.up.bind(this), window.addEventListener("mousemove", this.validValue.mouseMoveFunction), window.addEventListener("mouseup", this.validValue.mouseUpFunction), window.addEventListener("touchmove", this.validValue.mouseMoveFunction, !1), window.addEventListener("touchend", this.validValue.mouseUpFunction, !1);
  }
  move(e) {
    e instanceof TouchEvent && (e = e.touches[0]);
    let n = Math.round(e.pageX), i = Math.round(e.pageY);
    const r = this.validValue.startX, s = this.validValue.startY, o = this.validValue.startTime, c = this.validValue.end, d = this.validValue.bgImageWidth, l = this.validValue.trackArr;
    let u = n - r;
    const h = {
      x: n - r,
      y: i - s,
      type: "move",
      t: (/* @__PURE__ */ new Date()).getTime() - o.getTime()
    };
    l.push(h), u < 0 ? u = 0 : u > c && (u = c), this.validValue.moveX = u, this.validValue.movePercent = u / d, document.getElementById("tianai-slider-move-btn").style.transform = "translate(" + u + "px, 0px)", this.config[this.generateData.type].onMove.bind(this)();
  }
  sliderMove() {
    const e = this.validValue.moveX;
    document.getElementById("tianai-image-content-target").style.transform = "translate(" + e + "px, 0px)";
  }
  rotateMove() {
    const e = this.validValue.moveX;
    document.getElementById("image-content-target-template").style.transform = "rotate(" + e / (this.validValue.end / 360) + "deg)";
  }
  concatMove() {
    const e = this.validValue.moveX;
    document.getElementById("tianai-image-content-target").style.backgroundPositionX = e + "px";
  }
  up(e) {
    window.removeEventListener("mousemove", this.validValue.mouseMoveFunction), window.removeEventListener("mouseup", this.validValue.mouseUpFunction), window.removeEventListener("touchmove", this.validValue.mouseMoveFunction), window.removeEventListener("touchend", this.validValue.mouseUpFunction), e instanceof TouchEvent && (e = e.changedTouches[0]), this.validValue.stopTime = /* @__PURE__ */ new Date();
    let n = Math.round(e.pageX), i = Math.round(e.pageY);
    const r = this.validValue.startX, s = this.validValue.startY, o = this.validValue.startTime, c = this.validValue.trackArr, d = {
      x: n - r,
      y: i - s,
      type: "up",
      t: (/* @__PURE__ */ new Date()).getTime() - o.getTime()
    };
    c.push(d), this.valid();
  }
  valid() {
    const e = {
      bgImageWidth: this.validValue.bgImageWidth,
      bgImageHeight: this.validValue.bgImageHeight,
      templateImageWidth: this.generateData.templateImageWidth,
      templateImageHeight: this.generateData.templateImageHeight,
      startTime: this.validValue.startTime.getTime(),
      //this.formatDate(this.validValue.startTime, this.config.dateFormat),
      stopTime: this.validValue.stopTime.getTime(),
      //this.formatDate(this.validValue.stopTime, this.config.dateFormat),
      trackList: this.validValue.trackArr
    };
    let n = document.getElementById("tianai-content-image-wrapper-alert");
    n && this.fadeOut(n), this.alertTimeout && (clearTimeout(this.alertTimeout), delete this.alertTimeout), this.loading(this.config.validText), this.http.post("/captcha/clientVerify?" + this.config.postConfig.tokenParamName + "=" + this.config.token, e).then((i) => this.showResult(i.data)).catch((i) => {
      this.config.failRefreshCaptcha && this.generateCaptcha(), this.config.fail(i);
    });
  }
  formatDate(e, n) {
    const i = e.getFullYear(), r = String(e.getMonth() + 1).padStart(2, "0"), s = String(e.getDate()).padStart(2, "0"), o = String(e.getHours()).padStart(2, "0"), c = String(e.getMinutes()).padStart(2, "0"), d = String(e.getSeconds()).padStart(2, "0");
    return n = n.replace("yyyy", i), n = n.replace("MM", r), n = n.replace("dd", s), n = n.replace("HH", o), n = n.replace("mm", c), n = n.replace("ss", d), n;
  }
  removeLoading() {
    const e = document.getElementById("tianai-loading");
    e && this.fadeOut(e);
  }
  loading(e) {
    if (document.getElementById("tianai-loading"))
      return;
    let i = document.getElementById("tianai-content");
    const r = `
    <div class="__tianai-content-loading" id="tianai-loading">
      <div class="__tianai-content-loading-loader"></div>
      <div class="__tianai-content-loading-text">
        ${e}
      </div>
    </div>
    `;
    this.fadeIn(i.lastElementChild), i.insertAdjacentHTML("beforeend", r);
  }
  showResult(e) {
    const n = document.getElementById("tianai-content-image-wrapper"), i = `
      <div class="__tianai-content-image-wrapper-alert ${e.executeCode === "200" ? "success" : "error"}" id="tianai-content-image-wrapper-alert">
        ${e.message}
      </div>
    `;
    n.insertAdjacentHTML("beforeend", i);
    const r = n.lastElementChild;
    this.fadeIn(r), this.alertTimeout = setTimeout(() => this.fadeOut(r), 3e3), e.executeCode === "200" ? (this.removeLoading(), this.config.success(e)) : (this.config.fail(e), this.config.failRefreshCaptcha && this.generateCaptcha(!1));
  }
}
window.TianaiCaptcha = Kt;
