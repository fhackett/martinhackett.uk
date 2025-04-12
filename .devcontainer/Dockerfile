FROM ubuntu:latest

# https://code.visualstudio.com/remote/advancedcontainers/add-nonroot-user#_change-the-uidgid-of-an-existing-container-user
ARG USERNAME=ubuntu
ARG USER_UID=1000
ARG USER_GID=$USER_UID

RUN groupmod --gid $USER_GID $USERNAME \
    && usermod --uid $USER_UID --gid $USER_GID $USERNAME \
    && chown -R $USER_UID:$USER_GID /home/$USERNAME

RUN apt-get update \
    && apt-get install -y openjdk-21-jdk openjdk-21-source git build-essential curl zip

USER $USERNAME

RUN mkdir -p /home/$USERNAME \
    && chown $USERNAME /home/$USERNAME \
    && curl -sSLf https://scala-cli.virtuslab.org/get | sh \
    && . ~/.profile \
    && scala-cli version

RUN curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.40.2/install.sh | bash \
    && export NVM_DIR="$HOME/.nvm" \
    && [ -s "$NVM_DIR/nvm.sh" ] && . "$NVM_DIR/nvm.sh" \
    && [ -s "$NVM_DIR/bash_completion" ] && . "$NVM_DIR/bash_completion" \
    && nvm install 22 \
    && npm -v
