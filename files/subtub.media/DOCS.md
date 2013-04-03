# subtub.media docs

## Content

- [Logos](#logos)
- [HTML embed](#html-embed)
- [Development](#development)

## Logos
**SVG**

![subtub_logo_100px.png](https://raw.github.com/subtub/subtub.media/master/logos/subtub_logo_100px.png)  
subtub_logo.svg  
Source: https://raw.github.com/subtub/subtub.media/master/logos/subtub_logo.svg  

**PNG**

![subtub_logo_100px.png](https://raw.github.com/subtub/subtub.media/master/logos/subtub_logo_100px.png)  
subtub_logo_100px.png  
Source: https://raw.github.com/subtub/subtub.media/master/logos/subtub_logo_100px.png  

![subtub_logo_200px.png](https://raw.github.com/subtub/subtub.media/master/logos/subtub_logo_200px.png)  
subtub_logo_200px.png  
Source: https://raw.github.com/subtub/subtub.media/master/logos/subtub_logo_200px.png  

![subtub_logo_400px.png](https://raw.github.com/subtub/subtub.media/master/logos/subtub_logo_400px.png)  
subtub_logo_400px.png  
Source: https://raw.github.com/subtub/subtub.media/master/logos/subtub_logo_400px.png  


## HTML embed
**Image:**

subtub_logo_100px.png
```
<img src="https://raw.github.com/subtub/subtub.media/master/logos/subtub_logo_100px.png" alt="subtub logo">
```
subtub_logo_200px.png
```
<img src="https://raw.github.com/subtub/subtub.media/master/logos/subtub_logo_200px.png" alt="subtub logo">
```
subtub_logo_400px.png
```
<img src="https://raw.github.com/subtub/subtub.media/master/logos/subtub_logo_400px.png" alt="subtub logo">
```

**Button:**

subtub_logo_100px.png
```
<a class="subtub-logo" href="http://subtub.github.com/">
  <img src="https://raw.github.com/subtub/subtub.media/master/logos/subtub_logo_100px.png" alt="subtub">
</a>
```
subtub_logo_200px.png
```
<a class="subtub-logo" href="http://subtub.github.com/">
  <img src="https://raw.github.com/subtub/subtub.media/master/logos/subtub_logo_200px.png" alt="subtub">
</a>
```
subtub_logo_400px.png
```
<a class="subtub-logo" href="http://subtub.github.com/">
  <img src="https://raw.github.com/subtub/subtub.media/master/logos/subtub_logo_400px.png" alt="subtub">
</a>
```

## Development

**Quick Start**

Clone the repository with git by running:

	git clone git@github.com:subtub/subtub.media.git

Switch into develop branch

	git checkout develop

**Add features, update versioning**  

work and test at ```develop``` branch, commit, fix it. If the feature work is finished, merge it into master.  

it is strictly ensured that a strict versioning is maintained if a branches is merged. if the dev branch is merged into the master, add a tag for versioning.  

if the master is merged correctly, add a tag to master
```
git tag -a vX.Y.Z -m "Version X.Y.Z release" 
```

you can link it from this repository by running:
```
TODO:
git submodule add link
```
