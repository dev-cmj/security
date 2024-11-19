module.exports = {
    root: true,
    extends: [
        'plugin:vue/vue3-essential',    // Vue3에서 추천하는 규칙 추가
        'eslint:recommended',   // eslint 팀에서 추천하는 규칙 추가
        '@vue/eslint-config-prettier',  // eslint와 prettier 규칙 충돌을 방지함
    ],
    env: {
        'vue/setup-compiler-macros': true,
    },
    rules: {
        'no-console': 'warn',
        'prettier/prettier': [
            'error',
            {
                singleQuote: false,
                semi: true,
                useTabs: false,
                tabWidth: 2,
                trailingComma: 'all',
                printWidth: 120,
                bracketSpacing: true,
                arrowParens: 'avoid',
                endOfLine: 'auto',
            },
        ],
    },
};
