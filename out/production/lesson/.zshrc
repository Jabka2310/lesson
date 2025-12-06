# Path to your Oh My Zsh installation
export ZSH="$HOME/.oh-my-zsh"

# Set theme (лучшие для Ubuntu)
ZSH_THEME="agnoster"  # альтернативы: "powerlevel10k", "bira", "af-magic"

# Uncomment to disable auto-updates
# zstyle ':omz:update' mode disabled

# Auto-update every 7 days
zstyle ':omz:update' frequency 7

# Disable marking untracked files as dirty (ускоряет Git)
DISABLE_UNTRACKED_FILES_DIRTY="true"

# Plugins (рекомендуемые для Ubuntu)
plugins=(
    git
    zsh-autosuggestions
    zsh-syntax-highlighting
    sudo
    copyfile
    dirhistory
    common-aliases
    ubuntu
)

# Load Oh My Zsh
source $ZSH/oh-my-zsh.sh

# User configuration -----------------------------------------------------------

# Set default editor
export EDITOR='nano'  # или 'vim'/'code'

# Language environment (важно для Ubuntu)
export LANG=en_US.UTF-8
export LC_ALL=en_US.UTF-8

# Add directories to PATH
export PATH="$HOME/bin:$HOME/.local/bin:/usr/local/bin:$PATH"

# Ubuntu-specific aliases
alias update='sudo apt update && sudo apt upgrade -y'
alias install='sudo apt install'
alias remove='sudo apt remove'
alias purge='sudo apt purge'
alias clean='sudo apt autoremove -y && sudo apt clean'
alias reboot='sudo reboot'
alias shutdown='sudo shutdown now'

# Improved ls commands
alias ll='ls -alFh --color=auto'
alias la='ls -A --color=auto'
alias l='ls -CF --color=auto'

# Git shortcuts
alias gs='git status'
alias ga='git add'
alias gc='git commit -m'
alias gp='git push'
alias gpl='git pull'
alias gcl='git clone'

# Python (Ubuntu uses python3 by default)
alias python='python3'
alias pip='pip3'

# Safety first
alias rm='rm -i'
alias cp='cp -i'
alias mv='mv -i'

# Quick config editing
alias zshconfig="nano ~/.zshrc"
alias ohmyzsh="nano ~/.oh-my-zsh"

# Docker (если установлен)
alias dk='docker'
alias dkc='docker compose'

# Для темы agnoster - исправление отображения символов
POWERLINE_DETECTED_TERM="xterm-256color"

# Установка правильного TERM для WSL (если используется)
if grep -q Microsoft /proc/version; then
    export TERM=xterm-256color
fi
